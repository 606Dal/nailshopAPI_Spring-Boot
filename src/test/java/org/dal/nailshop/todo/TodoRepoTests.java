package org.dal.nailshop.todo;

import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.todo.entities.TodoEntity;
import org.dal.nailshop.todo.repository.TodoRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
@Transactional
public class TodoRepoTests {

    @Autowired
    private TodoRepository repository;

    @Disabled
    @Test
    @Commit
    public void testInsert() {

        for(int i = 0; i < 50; i++) {

            TodoEntity todo = TodoEntity.builder()
                    .title("제목" + i)
                    .writer("user1")
                    .build();

            repository.save(todo);
        } // end for
    }

    @Test
    public void testRead() {

        Optional<TodoEntity> result = repository.findById(7L);

        log.info(result.get());
    }

    @Test
    public void testDelete() {
        Long tno = 7L;

        repository.deleteById(tno);
    }

    @Test
    @Commit
    public void testUpdate() {
        Optional<TodoEntity> result = repository.findById(2L);

        TodoEntity todo = result.get();

        todo.changeTitle("오늘 날씨: 맑음");
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        List<TodoEntity> result = repository.todoList(pageable);

        log.info("-------------------------------");
        log.info(result);
    }

    @Test
    public void tsetSearchTitle() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        Page<TodoEntity> result = repository.listOfTitle("2", pageable);

        log.info("-------------------------------");
        log.info(result.getContent());
    }

    @Test
    public void testSelectDTO() {
        log.info(repository.selectDTO(1L));
    }
}
