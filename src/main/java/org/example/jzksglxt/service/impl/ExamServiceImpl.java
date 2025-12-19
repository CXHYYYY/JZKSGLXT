package org.example.jzksglxt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jzksglxt.dto.ExamConfigDTO;
import org.example.jzksglxt.dto.ExamDTO;
import org.example.jzksglxt.entity.*;
import org.example.jzksglxt.repository.*;
import org.example.jzksglxt.service.ExamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamQuestionRepository examQuestionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private QuestionTypeRepository questionTypeRepository;
    // 使用Spring Boot提供的Jackson支持，不需要手动注入ObjectMapper

    @Override
    public Exam createExam(ExamConfigDTO examConfigDTO) {
        Exam exam = new Exam();
        exam.setExamName(examConfigDTO.getExamName());
        exam.setSubjectId(examConfigDTO.getSubjectId());
        exam.setDuration(examConfigDTO.getDuration());
        exam.setQuestionCount(examConfigDTO.getQuestionCount());
        exam.setTotalScore(examConfigDTO.getTotalScore());
        exam.setPassScore(examConfigDTO.getPassScore());
        // 保存用户自定义的题型数量
        exam.setSingleChoiceCount(examConfigDTO.getSingleChoiceCount());
        exam.setMultipleChoiceCount(examConfigDTO.getMultipleChoiceCount());
        exam.setTrueFalseCount(examConfigDTO.getTrueFalseCount());
        // 保存每题分值
        exam.setScorePerQuestion(examConfigDTO.getScorePerQuestion());
        
        // 根据subjectId获取subject对象，设置subjectName字段
        Subject subject = subjectRepository.findById(examConfigDTO.getSubjectId()).orElse(null);
        if (subject != null) {
            exam.setSubjectName(subject.getSubjectName());
        }
        
        return examRepository.save(exam);
    }

    @Override
    @Transactional
    public boolean generateExamQuestions(Long examId) {
        try {
            // 获取考试配置
            Exam exam = examRepository.findById(examId).orElse(null);
            if (exam == null) {
                return false;
            }

            // 删除原有题目
            examQuestionRepository.deleteByExam_ExamId(examId);
            
            // 重置exam_question表自增计数器为1
            examQuestionRepository.resetAutoIncrement(1L);

            // 根据科目获取题目类型
            QuestionType singleChoiceType = questionTypeRepository.findByTypeName("单选题").orElse(null);
            QuestionType multipleChoiceType = questionTypeRepository.findByTypeName("多选题").orElse(null);
            QuestionType trueFalseType = questionTypeRepository.findByTypeName("判断题").orElse(null);

            if (singleChoiceType == null || trueFalseType == null) {
                return false;
            }

            // 使用用户自定义的题型数量
            int singleChoiceCount = exam.getSingleChoiceCount() != null ? exam.getSingleChoiceCount() : 0;
            int multipleChoiceCount = exam.getMultipleChoiceCount() != null ? exam.getMultipleChoiceCount() : 0;
            int trueFalseCount = exam.getTrueFalseCount() != null ? exam.getTrueFalseCount() : 0;

            // 随机获取题目
            List<Question> singleChoiceQuestions = getRandomQuestions(exam.getSubjectId(), singleChoiceType.getTypeId(), singleChoiceCount);
            List<Question> multipleChoiceQuestions = multipleChoiceType != null ? getRandomQuestions(exam.getSubjectId(), multipleChoiceType.getTypeId(), multipleChoiceCount) : new ArrayList<>();
            List<Question> trueFalseQuestions = getRandomQuestions(exam.getSubjectId(), trueFalseType.getTypeId(), trueFalseCount);

            // 合并题目
            List<Question> allQuestions = new ArrayList<>();
            allQuestions.addAll(singleChoiceQuestions);
            allQuestions.addAll(multipleChoiceQuestions);
            allQuestions.addAll(trueFalseQuestions);

            // 打乱题目顺序
            Random random = new Random();
            for (int i = allQuestions.size() - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                Question temp = allQuestions.get(i);
                allQuestions.set(i, allQuestions.get(j));
                allQuestions.set(j, temp);
            }

            // 创建ObjectMapper实例
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

            // 保存考试题目
            for (int i = 0; i < allQuestions.size(); i++) {
                Question question = allQuestions.get(i);
                ExamQuestion examQuestion = new ExamQuestion();
                examQuestion.setExam(exam);
                examQuestion.setQuestion(question);
                examQuestion.setQuestionNo(question.getQuestionNo());
                examQuestion.setSubjectId(exam.getSubjectId());
                // 获取科目名称
                Subject subject = subjectRepository.findById(exam.getSubjectId()).orElse(null);
                if (subject != null) {
                    examQuestion.setSubjectName(subject.getSubjectName());
                }
                examQuestion.setTypeId(question.getQuestionType().getTypeId());
                examQuestion.setTypeName(question.getQuestionType().getTypeName());
                examQuestion.setContent(question.getContent());
                examQuestion.setImageUrl(question.getImageUrl());
                examQuestion.setQuestionOrder(i + 1);
                
                // 使用统一的每题分值
                Integer score;
                if (exam.getScorePerQuestion() != null) {
                    // 如果设置了每题分值，使用该分值
                    score = exam.getScorePerQuestion();
                } else {
                    // 否则使用平均分
                    score = exam.getTotalScore() / exam.getQuestionCount();
                }
                
                examQuestion.setScore(score);

                // 转换选项为JSON
                List<QuestionOption> options = question.getOptions();
                String optionsJson = objectMapper.writeValueAsString(options);
                examQuestion.setOptionsJson(optionsJson);

                // 生成正确答案
                StringBuilder correctAnswer = new StringBuilder();
                for (QuestionOption option : options) {
                    if (option.getIsCorrect()) {
                        correctAnswer.append(option.getOptionCode());
                    }
                }
                examQuestion.setCorrectAnswer(correctAnswer.toString());

                examQuestionRepository.save(examQuestion);
            }

            // 不需要更新考试状态，因为数据库表中没有status字段
            // examRepository.save(exam);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Page<ExamDTO> getExamList(Pageable pageable) {
        Page<Exam> examPage = examRepository.findAll(pageable);
        return examPage.map(this::convertToExamDTO);
    }

    @Override
    public ExamDTO getExamById(Long examId) {
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            return null;
        }
        return convertToExamDTO(exam);
    }

    @Override
    public List<ExamQuestion> getExamQuestions(Long examId) {
        return examQuestionRepository.findByExam_ExamId(examId);
    }

    @Override
    @Transactional
    public boolean deleteExam(Long examId) {
        try {
            // 删除考试题目
            examQuestionRepository.deleteByExam_ExamId(examId);
            // 删除考试配置
            examRepository.deleteById(examId);
            
            // 重置exam表自增计数器
            // 查询剩余记录中的最大ID
            List<Exam> remainingExams = examRepository.findAll();
            Long newExamAutoIncrementValue;
            if (remainingExams.isEmpty()) {
                // 如果没有剩余记录，重置为1
                newExamAutoIncrementValue = 1L;
            } else {
                // 否则，找到最大ID并设置为maxId + 1
                Long maxId = remainingExams.stream()
                        .mapToLong(Exam::getExamId)
                        .max()
                        .orElse(0L);
                newExamAutoIncrementValue = maxId + 1;
            }
            
            // 重置exam表自增计数器
            examRepository.resetAutoIncrement(newExamAutoIncrementValue);
            
            // 重置exam_question表自增计数器
            // 查询剩余记录中的最大ID
            List<ExamQuestion> remainingExamQuestions = examQuestionRepository.findAll();
            Long newExamQuestionAutoIncrementValue;
            if (remainingExamQuestions.isEmpty()) {
                // 如果没有剩余记录，重置为1
                newExamQuestionAutoIncrementValue = 1L;
            } else {
                // 否则，找到最大ID并设置为maxId + 1
                Long maxId = remainingExamQuestions.stream()
                        .mapToLong(ExamQuestion::getExamQuestionId)
                        .max()
                        .orElse(0L);
                newExamQuestionAutoIncrementValue = maxId + 1;
            }
            
            // 重置exam_question表自增计数器
            examQuestionRepository.resetAutoIncrement(newExamQuestionAutoIncrementValue);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 随机获取题目
    private List<Question> getRandomQuestions(Integer subjectId, Integer typeId, int count) {
        // 获取该科目该题型的所有题目
        List<Question> allQuestions = questionRepository.findBySubject_SubjectIdAndQuestionType_TypeId(subjectId, typeId, org.springframework.data.domain.PageRequest.of(0, 1000)).getContent();
        
        if (allQuestions.size() <= count) {
            return allQuestions;
        }

        // 随机选择count个题目
        Random random = new Random();
        List<Question> selectedQuestions = new ArrayList<>();
        List<Integer> selectedIndices = new ArrayList<>();
        
        while (selectedIndices.size() < count) {
            int index = random.nextInt(allQuestions.size());
            if (!selectedIndices.contains(index)) {
                selectedIndices.add(index);
                selectedQuestions.add(allQuestions.get(index));
            }
        }
        
        return selectedQuestions;
    }

    // 转换为DTO
    private ExamDTO convertToExamDTO(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        BeanUtils.copyProperties(exam, examDTO);
        // 由于Exam实体和ExamDTO的字段名称不完全一致，需要手动设置一些字段
        examDTO.setTotalQuestions(exam.getQuestionCount());
        return examDTO;
    }
}
