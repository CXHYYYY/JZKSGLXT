package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {
    Optional<QuestionType> findByTypeName(String typeName);
}