package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    // 根据科目ID查询题目
    Page<Question> findBySubject_SubjectId(Integer subjectId, Pageable pageable);
    
    // 根据题型ID查询题目
    Page<Question> findByQuestionType_TypeId(Integer typeId, Pageable pageable);
    
    // 根据科目和题型查询题目
    Page<Question> findBySubject_SubjectIdAndQuestionType_TypeId(Integer subjectId, Integer typeId, Pageable pageable);
    
    // 根据题目编号查询题目
    Question findByQuestionNo(String questionNo);
    
    // 自定义查询方法，根据科目ID、题型ID和关键词查询题目
    @Query("SELECT q FROM Question q WHERE (:subjectId IS NULL OR q.subject.subjectId = :subjectId) AND (:typeId IS NULL OR q.questionType.typeId = :typeId) AND (:keyword IS NULL OR q.content LIKE %:keyword%)")
    Page<Question> findQuestions(@Param("subjectId") Integer subjectId, 
                                @Param("typeId") Integer typeId, 
                                @Param("keyword") String keyword, 
                                Pageable pageable);
    
    /**
     * 重置自增计数器
     * @param value 新的自增起始值
     */
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = ?1", nativeQuery = true)
    void resetAutoIncrement(Long value);
}