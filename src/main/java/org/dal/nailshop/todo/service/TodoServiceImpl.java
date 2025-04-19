package org.dal.nailshop.todo.service;

import lombok.RequiredArgsConstructor;
import org.dal.nailshop.todo.dto.TodoDTO;
import org.dal.nailshop.todo.entities.TodoEntity;
import org.dal.nailshop.todo.repository.TodoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;

    @Override
    public TodoDTO getTodoDTO(Long tno) {
        return repository.selectDTO(tno);
    }

    @Override
    public List<TodoDTO> getTodoList(Pageable pageable) {
        List<TodoEntity> entityList = repository.todoList(pageable);
        return entityList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long addTodo(TodoDTO dto) {
        TodoEntity entity = TodoEntity.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .build();

        TodoEntity saved = repository.save(entity);
        return saved.getTno();
    }

    @Override
    public TodoDTO readTodo(Long tno) {

        if (!repository.existsById(tno)) {
            throw new RuntimeException("존재하지 않는 글입니다: " + tno);
        }

        return repository.selectDTO(tno);
    }

    @Override
    public Long updateTodo(TodoDTO dto) {
        TodoEntity entity = repository.findById(dto.getTno())
                .orElseThrow();
        entity.changeTitle(dto.getTitle());
        repository.save(entity);

        return dto.getTno();
    }

    @Override
    public Long deleteTodo(Long tno) {

        if (!repository.existsById(tno)) {
            throw new RuntimeException("존재하지 않는 글입니다: " + tno);
        }

        repository.deleteById(tno);

        return tno;
    }

    private TodoDTO convertToDTO(TodoEntity entity) {
        return new TodoDTO(
                entity.getTno(),
                entity.getTitle(),
                entity.getWriter(),
                entity.getRegDate(),
                entity.getModDate()
        );
    }


}
