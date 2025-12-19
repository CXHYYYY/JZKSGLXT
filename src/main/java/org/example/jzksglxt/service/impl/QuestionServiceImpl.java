package org.example.jzksglxt.service.impl;

import org.example.jzksglxt.entity.Question;
import org.example.jzksglxt.entity.QuestionOption;
import org.example.jzksglxt.entity.QuestionType;
import org.example.jzksglxt.entity.Subject;
import org.example.jzksglxt.repository.QuestionOptionRepository;
import org.example.jzksglxt.repository.QuestionRepository;
import org.example.jzksglxt.repository.QuestionTypeRepository;
import org.example.jzksglxt.repository.SubjectRepository;
import org.example.jzksglxt.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 题目服务实现类
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Override
    public Page<Question> getQuestionList(Integer subjectId, Integer typeId, String keyword, Pageable pageable) {
        return questionRepository.findQuestions(subjectId, typeId, keyword, pageable);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Question addQuestion(Question question) {
        // 保存题目
        Question savedQuestion = questionRepository.save(question);
        // 保存选项
        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
            for (QuestionOption option : question.getOptions()) {
                option.setQuestion(savedQuestion);
                questionOptionRepository.save(option);
            }
        }
        return savedQuestion;
    }

    @Override
    @Transactional
    public Question updateQuestion(Question question) {
        // 保存题目
        Question updatedQuestion = questionRepository.save(question);
        // 删除原有选项
        questionOptionRepository.deleteByQuestion_QuestionId(updatedQuestion.getQuestionId());
        // 保存新选项
        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
            for (QuestionOption option : question.getOptions()) {
                option.setQuestion(updatedQuestion);
                questionOptionRepository.save(option);
            }
        }
        return updatedQuestion;
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        // 删除题目（级联删除选项）
        questionRepository.deleteById(id);
    }

    @Override
    public List<Subject> getSubjectList() {
        return subjectRepository.findAll();
    }

    @Override
    public List<QuestionType> getQuestionTypeList() {
        return questionTypeRepository.findAll();
    }

    @Override
    public Subject getSubjectById(Integer subjectId) {
        return subjectRepository.findById(subjectId).orElse(null);
    }

    @Override
    public QuestionType getQuestionTypeById(Integer typeId) {
        return questionTypeRepository.findById(typeId).orElse(null);
    }
}