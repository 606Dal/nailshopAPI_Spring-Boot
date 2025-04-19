package org.dal.nailshop.todo.service;

import jakarta.transaction.Transactional;
import org.dal.nailshop.todo.dto.TodoDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Transactional
public interface TodoService {

    TodoDTO getTodoDTO(Long tno);

    List<TodoDTO> getTodoList(Pageable pageable);

    public Long addTodo(TodoDTO dto);

    public TodoDTO readTodo(Long tno);

    public Long updateTodo(TodoDTO dto);

    public Long deleteTodo(Long tno);

}
