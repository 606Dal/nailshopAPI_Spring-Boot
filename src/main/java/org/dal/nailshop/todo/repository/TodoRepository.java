package org.dal.nailshop.todo.repository;

import org.dal.nailshop.todo.dto.TodoDTO;
import org.dal.nailshop.todo.entities.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoSearch {

    @Query("select t from TodoEntity t where t.title like %:title%")
    Page<TodoEntity> listOfTitle(@Param("title") String title, Pageable pageable);

    @Query("select new org.dal.nailshop.todo.dto.TodoDTO(t.tno, t.title, t.writer, t.regDate, t.modDate) from TodoEntity t where t.tno = :tno")
    TodoDTO selectDTO( @Param("tno") Long tno  );

}
