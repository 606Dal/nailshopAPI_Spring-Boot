package org.dal.nailshop.todo.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.todo.entities.QTodoEntity;
import org.dal.nailshop.todo.entities.TodoEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class TodoSearchImpl implements TodoSearch {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TodoEntity> todoList(Pageable pageable) {
        log.info("list....................");
        log.info(queryFactory);

        QTodoEntity todo = QTodoEntity.todoEntity;

        JPQLQuery<TodoEntity> query = queryFactory.selectFrom(todo);

        //페이징
        int size = pageable.getPageSize();
        int offset = pageable.getPageNumber() * size;

        query.limit(size);
        query.offset(offset);
        query.orderBy(new OrderSpecifier<>(Order.DESC, todo.tno));

        List<TodoEntity> list = query.fetch();

        return list;
    }
}
