package com.nguyendinhkien.postgresdemo.repository;

import com.nguyendinhkien.postgresdemo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
