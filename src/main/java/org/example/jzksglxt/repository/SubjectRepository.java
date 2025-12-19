package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    // 可以根据需要添加自定义查询方法
}