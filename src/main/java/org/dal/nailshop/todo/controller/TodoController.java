package org.dal.nailshop.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.todo.dto.ActionResultDTO;
import org.dal.nailshop.todo.dto.TodoDTO;
import org.dal.nailshop.todo.entities.TodoEntity;
import org.dal.nailshop.todo.repository.TodoRepository;
import org.dal.nailshop.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping("list")
    public ResponseEntity<List<TodoDTO>> list(Pageable pageable) {

        List<TodoDTO> list = service.getTodoList(pageable);

        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity<ActionResultDTO<Long>> add(@RequestBody TodoDTO dto) {

        log.info("===========post==============");
        log.info(dto.toString());

        Long savedId = service.addTodo(dto); // 실제 저장

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                .result("Add Success")
                .data(savedId)
                .build());
    }

    @GetMapping("read/{tno}")
    public ResponseEntity<TodoDTO> read(@PathVariable Long tno) {
        TodoDTO dto = service.readTodo(tno);

        if (dto != null) {
            return ResponseEntity.ok(dto); // 200 OK + body에 dto 담김
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("update")
    public ResponseEntity<ActionResultDTO<Long>> update(@RequestBody TodoDTO todoDTO) {

        Long tno = service.updateTodo(todoDTO);

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                .result("Update Success")
                .data(tno)
                .build()
        );
    }

    @DeleteMapping("/{tno}")
    public ResponseEntity<ActionResultDTO<Long>> delete(@PathVariable Long tno) {

        Long deletedId = service.deleteTodo(tno);

        return ResponseEntity.ok(
                ActionResultDTO.<Long>builder()
                        .result("Delete Success")
                        .data(deletedId)
                        .build()
        );
    }
}
