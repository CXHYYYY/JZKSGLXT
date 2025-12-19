package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
    List<ExamQuestion> findByExam_ExamId(Long examId);
    void deleteByExam_ExamId(Long examId);
    
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE exam_question AUTO_INCREMENT = ?1", nativeQuery = true)
    void resetAutoIncrement(Long value);
}
