package org.dal.nailshop.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductReviewAddDTO;
import org.dal.nailshop.product.dto.ProductReviewListDTO;
import org.dal.nailshop.product.dto.ProductReviewModifyDTO;
import org.dal.nailshop.product.dto.ProductReviewReadDTO;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.dal.nailshop.product.entities.ProductReviewImage;
import org.dal.nailshop.product.repository.ProductReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository reviewRepository;

    @Override
    public Page<ProductReviewListDTO> getListOfProduct(Long pno, Pageable pageable) {

        if (pno == null) {
            throw new NoSuchElementException("해당 상품이 없습니다.");
        }

        return reviewRepository.findByProductPno(pno, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<ProductReviewListDTO> reviewList(Long pno, PageRequestDTO pageRequestDTO) {

        if (pno == null) {
            throw new NoSuchElementException("해당 상품이 없습니다.");
        }

        return reviewRepository.reviewList(pno, pageRequestDTO);
    }

    @Override
    public ProductReviewReadDTO readReview(Long rno) {

        if (rno == null) {
            throw new NoSuchElementException("리뷰 번호(rno)가 없습니다.");
        }

        ProductReviewReadDTO dto = reviewRepository.selectOne(rno);

        if (dto != null) {
            return dto;
        } else {
            throw new NoSuchElementException("리뷰를 찾을 수 없습니다.");
        }
    }

    @Override
    public Long add(ProductReviewAddDTO dto) {

        ProductReviewEntity reviewEntity = addDTOToEntity(dto);

        String uploadDir = "C:\\nginx-1.26.3\\html\\reviewuploads";

        // 저장 경로
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // 디렉토리 없으면 생성
        }

        for (MultipartFile file : dto.getFiles()) {

            if (file.isEmpty()) continue;

            String contentType = file.getContentType();

            // 아미지 타입 확인
            if (contentType == null || !contentType.startsWith("image")) {
                continue;
            }

            String originalName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String savedName = uuid + "_" + originalName;

            File saveFile = new File(uploadDir, savedName);
            File thumbFile = new File(uploadDir, "s_" + savedName);

            try {
                file.transferTo(saveFile);
                // 썸네일 생성
                Thumbnails.of(saveFile)
                        .size(300, 300)
                        .toFile(thumbFile);

            } catch (IOException e) {
                throw new RuntimeException("리뷰 이미지 저장 중 오류 발생" + savedName, e);
            }

            // DB에는 파일명만 저장
            reviewEntity.addImage(savedName);
            dto.getImageNames().add(savedName);
        } // end for

        reviewRepository.save(reviewEntity);

        return reviewEntity.getRno();
    }

    @Override
    public void modify(ProductReviewModifyDTO dto) {

        Long rno = dto.getRno();

        ProductReviewEntity reviewEntity = reviewRepository.findById(rno)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 없음"));

        // 기존의 파일들을 알아놓아야, 필요 없는 파일 삭제 가능.
        List<String> oldFiles = reviewEntity.getImages()
                .stream()
                .map(ProductReviewImage::getImgName)
                .collect(Collectors.toList());
        log.info("기존 파일들 이름: {}", oldFiles);

        List<MultipartFile> files = dto.getFiles();

        // 업로드된 파일들
        List<String> uploadedFileNames = new ArrayList<>();

        // 이미지 파일 업로드 경로
        String uploadDir = "C:\\nginx-1.26.3\\html\\reviewuploads";

        // 기존 이미지 삭제
        reviewEntity.clearImages();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image")) {
                continue;
            }

            String originalName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String savedName = uuid + "_" + originalName;

            File saveFile = new File(uploadDir, savedName);
            File thumbFile = new File(uploadDir, "s_" + savedName);

            try {
                file.transferTo(saveFile);
                // 썸네일 생성
                Thumbnails.of(saveFile)
                        .size(300, 300)
                        .toFile(thumbFile);

                uploadedFileNames.add(savedName);

            } catch (IOException e) {
                throw new RuntimeException("상품 이미지 저장 중 오류 발생" + savedName, e);
            }

        } // end for

        // DB에는 파일명만 저장
        dto.getImageNames().addAll(uploadedFileNames);
        log.info("----------상품 수정------------");
        log.info(dto);

        reviewEntity.changeComment(dto.getComment());
        reviewEntity.changeScore(dto.getScore());
        dto.getImageNames().forEach(imgName -> reviewEntity.addImage(imgName));

        reviewRepository.save(reviewEntity);

        // 수정 후 유지해야 하는 파일 목록. 기존에 존재했지만 화면에서 삭제된 파일들은 지워야 함.
        List<String> imagesToKeep = dto.getImageNames();
        log.info("imagesToKeep 유지 목록: {}", imagesToKeep);

        // 기존 파일(oldFiles) 중에서 현재 남아 있어야 하는 파일(imagesToKeep)을 제외한 파일들을 걸러냄. -> 삭제할 파일들
        List<String> filesToDelete = oldFiles.stream()
                .filter(oldFile -> !imagesToKeep.contains(oldFile))
                .collect(Collectors.toList());

        log.info("------filteredList----------");
        log.info("삭제 대상 이미지: {}", filesToDelete);

        filesToDelete.forEach(targetFile -> {
            // 하드디스크의 파일 삭제
            new File(uploadDir + "\\" + targetFile).delete();
            new File(uploadDir + "\\s_" + targetFile).delete(); // 썸네일도 같이 삭제
        });
    }
}
