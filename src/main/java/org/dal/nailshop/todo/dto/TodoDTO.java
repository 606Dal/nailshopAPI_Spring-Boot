package org.dal.nailshop.todo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class TodoDTO {

    private Long tno;

    private String title;

    private String writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

//    private List<MultipartFile> file = new ArrayList<>();

    public TodoDTO(Long tno, String title, String writer, LocalDateTime regDate, LocalDateTime modDate) {
        this.tno = tno;
        this.title = title;
        this.writer = writer;
        this.regDate = regDate;
        this.modDate = modDate;
    }

//    public TodoDTO(Long tno, String title, String writer, LocalDateTime regDate, LocalDateTime modDate, List<MultipartFile> file) {
//        this.tno = tno;
//        this.title = title;
//        this.writer = writer;
//        this.regDate = regDate;
//        this.modDate = modDate;
//        this.file = file;
//    }
}
