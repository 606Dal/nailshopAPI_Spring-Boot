package org.dal.nailshop.todo.repository;

import org.dal.nailshop.todo.entities.TodoEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoSearch {

    List<TodoEntity> todoList(Pageable pageable);

}
