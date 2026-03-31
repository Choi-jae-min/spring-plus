package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.SearchCondition;
import org.example.expert.domain.todo.dto.response.TodoPageResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQueryRepository {
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoPageResponse> findTodosByCondition(Pageable pageable, SearchCondition searchCondition);
}
