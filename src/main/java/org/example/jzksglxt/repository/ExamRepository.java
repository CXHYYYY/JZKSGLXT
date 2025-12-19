package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ExamRepository extends JpaRepository<Exam, Long>, JpaSpecificationExecutor<Exam> {
    Page<Exam> findBySubjectId(Integer subjectId, Pageable pageable);
    
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE exam AUTO_INCREMENT = ?1", nativeQuery = true)
    void resetAutoIncrement(Long value);
}
