package org.dal.nailshop.todo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_todo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class TodoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    @Column(nullable = false, length = 300)
    private String title;

    private String writer;

    public void changeTitle(String title){
        this.title = title;
    }
}
