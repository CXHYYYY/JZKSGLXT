package org.example.jzksglxt.controller;

import org.example.jzksglxt.common.OperationLogUtil;
import org.example.jzksglxt.common.ResponseResult;
import org.example.jzksglxt.dto.QuestionDTO;
import org.example.jzksglxt.dto.QuestionListDTO;
import org.example.jzksglxt.entity.Question;
import org.example.jzksglxt.entity.Subject;
import org.example.jzksglxt.entity.QuestionOption;
import org.example.jzksglxt.entity.QuestionType;
import org.example.jzksglxt.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目管理控制器
 */
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 获取题目列表
     * @param subjectId 科目ID（可选）
     * @param typeId 题型ID（可选）
     * @param keyword 关键词（可选）
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @param adminId 管理员ID（从请求头获取）
     * @return 题目列表
     */
    @GetMapping("/list")
    public ResponseResult<Page<QuestionListDTO>> getQuestionList(@RequestParam(required = false) Integer subjectId,
                                                         @RequestParam(required = false) Integer typeId,
                                                         @RequestParam(required = false) String keyword,
                                                         @RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size,
                                                         @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        // 创建分页参数，按创建时间倒序排序
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Question> questionPage = questionService.getQuestionList(subjectId, typeId, keyword, pageable);
        
        // 将Question转换为QuestionListDTO，包含正确答案
        Page<QuestionListDTO> questionListDTOPage = questionPage.map(QuestionListDTO::new);
        

        
        return ResponseResult.success(questionListDTOPage);
    }

    /**
     * 获取题目详情
     * @param id 题目ID
     * @param adminId 管理员ID（从请求头获取）
     * @return 题目详情
     */
    @GetMapping("/{id}")
    public ResponseResult<Question> getQuestionById(@PathVariable Long id,
                                                   @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        Question question = questionService.getQuestionById(id);
        

        
        return ResponseResult.success(question);
    }

    /**
     * 添加题目
     * @param questionDTO 题目信息DTO
     * @param adminId 管理员ID（从请求头获取）
     * @return 添加结果
     */
    @PostMapping
    public ResponseResult<Question> addQuestion(@RequestBody QuestionDTO questionDTO,
                                              @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        // 转换为Question实体
        Question question = convertToEntity(questionDTO);
        Question addedQuestion = questionService.addQuestion(question);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "create", "question_management", "添加题目");
        }
        
        return ResponseResult.success(addedQuestion);
    }

    /**
     * 更新题目
     * @param id 题目ID
     * @param questionDTO 题目信息DTO
     * @param adminId 管理员ID（从请求头获取）
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ResponseResult<Question> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO,
                                                 @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        // 转换为Question实体
        Question question = convertToEntity(questionDTO);
        question.setQuestionId(id);
        Question updatedQuestion = questionService.updateQuestion(question);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "update", "question_management", "更新题目");
        }
        
        return ResponseResult.success(updatedQuestion);
    }
    
    /**
     * 将QuestionDTO转换为Question实体
     * @param questionDTO 题目DTO
     * @return Question实体
     */
    private Question convertToEntity(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionNo(questionDTO.getQuestionNo());
        question.setContent(questionDTO.getContent());
        question.setImageUrl(questionDTO.getImageUrl());
        
        // 设置科目实体
        if (questionDTO.getSubjectId() != null) {
            Subject subject = questionService.getSubjectById(questionDTO.getSubjectId());
            question.setSubject(subject);
        }
        
        // 设置题型实体
        if (questionDTO.getTypeId() != null) {
            QuestionType questionType = questionService.getQuestionTypeById(questionDTO.getTypeId());
            question.setQuestionType(questionType);
        }
        
        // 转换选项
        if (questionDTO.getOptions() != null) {
            List<QuestionOption> options = new ArrayList<>();
            for (QuestionDTO.QuestionOptionDTO optionDTO : questionDTO.getOptions()) {
                QuestionOption option = new QuestionOption();
                option.setOptionCode(optionDTO.getOptionCode());
                option.setContent(optionDTO.getContent());
                option.setIsCorrect(optionDTO.getIsCorrect());
                option.setQuestion(question);
                options.add(option);
            }
            question.setOptions(options);
        }
        
        return question;
    }

    /**
     * 删除题目
     * @param id 题目ID
     * @param adminId 管理员ID（从请求头获取）
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteQuestion(@PathVariable Long id,
                                              @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        questionService.deleteQuestion(id);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "delete", "question_management", "删除题目");
        }
        
        return ResponseResult.success("删除成功");
    }

    /**
     * 获取科目列表
     * @param adminId 管理员ID（从请求头获取）
     * @return 科目列表
     */
    @GetMapping("/subjects")
    public ResponseResult<List<Subject>> getSubjectList(@RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        List<Subject> subjectList = questionService.getSubjectList();
        

        
        return ResponseResult.success(subjectList);
    }

    /**
     * 获取题型列表
     * @param adminId 管理员ID（从请求头获取）
     * @return 题型列表
     */
    @GetMapping("/types")
    public ResponseResult<List<QuestionType>> getQuestionTypeList(@RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        List<QuestionType> typeList = questionService.getQuestionTypeList();
        

        
        return ResponseResult.success(typeList);
    }
}