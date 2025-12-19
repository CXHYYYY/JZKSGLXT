package org.example.jzksglxt.dto;

import org.example.jzksglxt.entity.QuestionType;
import org.example.jzksglxt.entity.Subject;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 选项DTO，用于避免循环引用
 */
class OptionDTO {
    private String optionCode;
    private String content;
    private Boolean isCorrect;

    public OptionDTO(String optionCode, String content, Boolean isCorrect) {
        this.optionCode = optionCode;
        this.content = content;
        this.isCorrect = isCorrect;
    }

    // Getters
    public String getOptionCode() {
        return optionCode;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }
}

/**
 * 试题列表DTO
 */
public class QuestionListDTO {

    private Long questionId;
    private String questionNo;
    private String content;
    private String imageUrl;
    private Date createdAt;
    private Subject subject;
    private QuestionType questionType;
    private String correctAnswer; // 正确答案（如：A, B, C 或 AB, AC 等）
    private List<OptionDTO> options; // 选项列表，使用DTO避免循环引用

    // 构造函数，用于从Question实体转换
    public QuestionListDTO(org.example.jzksglxt.entity.Question question) {
        this.questionId = question.getQuestionId();
        this.questionNo = question.getQuestionNo();
        this.content = question.getContent();
        this.imageUrl = question.getImageUrl();
        this.createdAt = question.getCreatedAt();
        this.subject = question.getSubject();
        this.questionType = question.getQuestionType();
        
        // 从选项中提取正确答案和转换选项列表
        if (question.getOptions() != null) {
            // 过滤出正确选项，按字母顺序排序，然后拼接成字符串
            List<String> correctOptions = question.getOptions().stream()
                    .filter(org.example.jzksglxt.entity.QuestionOption::getIsCorrect)
                    .map(org.example.jzksglxt.entity.QuestionOption::getOptionCode)
                    .sorted()
                    .collect(Collectors.toList());
            this.correctAnswer = String.join(", ", correctOptions);
            
            // 转换选项列表为DTO，避免循环引用
            this.options = question.getOptions().stream()
                    .map(option -> new OptionDTO(
                            option.getOptionCode(),
                            option.getContent(),
                            option.getIsCorrect()
                    ))
                    .collect(Collectors.toList());
        } else {
            this.correctAnswer = "";
            this.options = List.of();
        }
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}