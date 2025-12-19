package org.example.jzksglxt.service;

import org.example.jzksglxt.dto.ExamConfigDTO;
import org.example.jzksglxt.dto.ExamDTO;
import org.example.jzksglxt.entity.Exam;
import org.example.jzksglxt.entity.ExamQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamService {
    // 创建考试配置
    Exam createExam(ExamConfigDTO examConfigDTO);
    
    // 生成考试题目
    boolean generateExamQuestions(Long examId);
    
    // 获取考试列表
    Page<ExamDTO> getExamList(Pageable pageable);
    
    // 根据ID获取考试详情
    ExamDTO getExamById(Long examId);
    
    // 获取考试题目
    List<ExamQuestion> getExamQuestions(Long examId);
    
    // 删除考试
    boolean deleteExam(Long examId);
}
