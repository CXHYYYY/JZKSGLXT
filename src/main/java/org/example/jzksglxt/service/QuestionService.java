package org.example.jzksglxt.service;

import org.example.jzksglxt.entity.Question;
import org.example.jzksglxt.entity.Subject;
import org.example.jzksglxt.entity.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 题目服务接口
 */
public interface QuestionService {
    
    /**
     * 获取题目列表
     * @param subjectId 科目ID（可选）
     * @param typeId 题型ID（可选）
     * @param keyword 关键词（可选，用于搜索题目内容）
     * @param pageable 分页参数
     * @return 题目列表
     */
    Page<Question> getQuestionList(Integer subjectId, Integer typeId, String keyword, Pageable pageable);
    
    /**
     * 获取题目详情
     * @param id 题目ID
     * @return 题目详情
     */
    Question getQuestionById(Long id);
    
    /**
     * 添加题目
     * @param question 题目信息
     * @return 添加后的题目信息
     */
    Question addQuestion(Question question);
    
    /**
     * 更新题目
     * @param question 题目信息
     * @return 更新后的题目信息
     */
    Question updateQuestion(Question question);
    
    /**
     * 删除题目
     * @param id 题目ID
     */
    void deleteQuestion(Long id);
    
    /**
     * 获取科目列表
     * @return 科目列表
     */
    List<Subject> getSubjectList();
    
    /**
     * 获取题型列表
     * @return 题型列表
     */
    List<QuestionType> getQuestionTypeList();
    
    /**
     * 根据科目ID获取科目
     * @param subjectId 科目ID
     * @return 科目信息
     */
    Subject getSubjectById(Integer subjectId);
    
    /**
     * 根据题型ID获取题型
     * @param typeId 题型ID
     * @return 题型信息
     */
    QuestionType getQuestionTypeById(Integer typeId);
}