package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {
    // 可以根据需要添加自定义查询方法
}