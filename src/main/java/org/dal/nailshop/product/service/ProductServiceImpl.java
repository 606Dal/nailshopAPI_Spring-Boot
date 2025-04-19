package org.dal.nailshop.product.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.*;
import org.dal.nailshop.product.entities.ProductEntity;
import org.dal.nailshop.product.entities.ProductImage;
import org.dal.nailshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Transactional
@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Long add(ProductAddDTO dto) {

        ProductEntity productEntity = addDTOToEntity(dto);

        String uploadDir = "C:\\nginx-1.26.3\\html\\uploads";

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
                throw new RuntimeException("상품 이미지 저장 중 오류 발생" + savedName, e);
            }

            // DB에는 파일명만 저장
            productEntity.addImage(savedName);
            dto.getImageNames().add(savedName);
        } // end for

        repository.save(productEntity);

        return productEntity.getPno();
    }

    @Override
    public ProductReadDTO read(Long pno) {

        return new ProductReadDTO(repository.selectOne(pno));
    }

    @Override
    public PageResponseDTO<ProductListDTO> listProducts(PageRequestDTO pageRequestDTO) {

        return repository.productList(pageRequestDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<ProductListAllDTO> listProductsWithAllImages(PageRequestDTO pageRequestDTO) {

        return repository.listAllImages(pageRequestDTO);
    }

    @Override
    public void modify(ProductModifyDTO dto) {

        Long pno = dto.getPno();

        ProductEntity productEntity = repository.selectOne(pno);

        // 기존의 파일들을 알아놓아야, 필요 없는 파일 삭제 가능.
        List<String> oldFiles = productEntity.getImages()
                .stream()
                .map(ProductImage::getImgName)
                .collect(Collectors.toList());
        log.info("기존 파일들 이름: {}", oldFiles);

        List<MultipartFile> files = dto.getFiles();

        // 업로드된 파일들
        List<String> uploadedFileNames = new ArrayList<>();

        // 이미지 파일 업로드 경로
        String uploadDir = "C:\\nginx-1.26.3\\html\\uploads";

        // 기존 이미지 삭제
        productEntity.clearImages();

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

            // DB에는 파일명만 저장
//            productEntity.addImage(savedName);
//            log.info("저장 후 productEntity: {}", productEntity.getImages());

//            dto.getImageNames().add(savedName);
        } // end for

//        dto.getImageNames().add(uploadedFileNames.toString());
        dto.getImageNames().addAll(uploadedFileNames);
        log.info("----------상품 수정------------");
        log.info(dto);

        productEntity.changePname(dto.getPname());
        productEntity.changePdesc(dto.getPdesc());
        productEntity.changePrice(dto.getPrice());
//        repository.save(productEntity);

        // 수정 후 유지해야 하는 파일 목록. 기존에 존재했지만 화면에서 삭제된 파일들은 지워야 함.
        List<String> imagesToKeep = dto.getImageNames();
        log.info("imagesToKeep 유지 목록: {}", imagesToKeep);

        // 기존 파일(oldFiles) 중에서 현재 남아 있어야 하는 파일(recent)을 제외한 파일들을 걸러냄. -> 삭제할 파일들
        List<String> filesToDelete = oldFiles.stream()
                .filter(oldFile -> !imagesToKeep.contains(oldFile))
                .collect(Collectors.toList());

        log.info("------filteredList----------");
        log.info("삭제 대상 이미지: {}", filesToDelete);

//        filesToDelete.forEach(targetFile -> {
//            // 하드디스크의 파일 삭제
//            new File(uploadDir + targetFile).delete();
//            new File(uploadDir + "s_" + targetFile).delete(); // 썸네일도 같이 삭제
//        });


    }
}
