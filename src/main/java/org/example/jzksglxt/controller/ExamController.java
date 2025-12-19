package org.example.jzksglxt.controller;

import org.example.jzksglxt.dto.ExamConfigDTO;
import org.example.jzksglxt.dto.ExamDTO;
import org.example.jzksglxt.entity.Exam;
import org.example.jzksglxt.entity.ExamQuestion;
import org.example.jzksglxt.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    // 创建考试配置
    @PostMapping
    public ResponseEntity<Map<String, Object>> createExam(@RequestBody ExamConfigDTO examConfigDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            Exam exam = examService.createExam(examConfigDTO);
            result.put("success", true);
            result.put("message", "考试配置创建成功");
            result.put("data", exam);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "考试配置创建失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // 生成考试题目
    @PostMapping("/{examId}/generate")
    public ResponseEntity<Map<String, Object>> generateExamQuestions(@PathVariable Long examId) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = examService.generateExamQuestions(examId);
            if (success) {
                result.put("success", true);
                result.put("message", "考试题目生成成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "考试题目生成失败");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "考试题目生成失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // 获取考试列表
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getExamList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> result = new HashMap<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            var examPage = examService.getExamList(pageable);
            result.put("success", true);
            result.put("message", "获取考试列表成功");
            result.put("data", examPage.getContent());
            result.put("total", examPage.getTotalElements());
            result.put("pages", examPage.getTotalPages());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取考试列表失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // 根据ID获取考试详情
    @GetMapping("/{examId}")
    public ResponseEntity<Map<String, Object>> getExamById(@PathVariable Long examId) {
        Map<String, Object> result = new HashMap<>();
        try {
            ExamDTO examDTO = examService.getExamById(examId);
            if (examDTO != null) {
                result.put("success", true);
                result.put("message", "获取考试详情成功");
                result.put("data", examDTO);
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "考试不存在");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取考试详情失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // 获取考试题目
    @GetMapping("/{examId}/questions")
    public ResponseEntity<Map<String, Object>> getExamQuestions(@PathVariable Long examId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ExamQuestion> examQuestions = examService.getExamQuestions(examId);
            result.put("success", true);
            result.put("message", "获取考试题目成功");
            result.put("data", examQuestions);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取考试题目失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // 删除考试
    @DeleteMapping("/{examId}")
    public ResponseEntity<Map<String, Object>> deleteExam(@PathVariable Long examId) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = examService.deleteExam(examId);
            if (success) {
                result.put("success", true);
                result.put("message", "考试删除成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "考试删除失败");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "考试删除失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
