package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    // 根据题目ID查询选项
    List<QuestionOption> findByQuestion_QuestionId(Long questionId);
    
    // 根据题目ID删除选项
    void deleteByQuestion_QuestionId(Long questionId);
}