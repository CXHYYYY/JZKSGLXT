package org.example.jzksglxt.dto;

import java.util.Date;

public class ExamDTO {
    private Long examId;
    private String examName;
    private Integer subjectId;
    private String subjectName;
    private Integer totalQuestions;
    private Integer passScore;
    private Integer singleChoicePercentage;
    private Integer multipleChoicePercentage;
    private Integer trueFalsePercentage;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Integer duration;
    private Integer totalScore;
    private Integer singleChoiceCount;
    private Integer multipleChoiceCount;
    private Integer trueFalseCount;

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public Integer getSingleChoicePercentage() {
        return singleChoicePercentage;
    }

    public void setSingleChoicePercentage(Integer singleChoicePercentage) {
        this.singleChoicePercentage = singleChoicePercentage;
    }

    public Integer getMultipleChoicePercentage() {
        return multipleChoicePercentage;
    }

    public void setMultipleChoicePercentage(Integer multipleChoicePercentage) {
        this.multipleChoicePercentage = multipleChoicePercentage;
    }

    public Integer getTrueFalsePercentage() {
        return trueFalsePercentage;
    }

    public void setTrueFalsePercentage(Integer trueFalsePercentage) {
        this.trueFalsePercentage = trueFalsePercentage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getSingleChoiceCount() {
        return singleChoiceCount;
    }

    public void setSingleChoiceCount(Integer singleChoiceCount) {
        this.singleChoiceCount = singleChoiceCount;
    }

    public Integer getMultipleChoiceCount() {
        return multipleChoiceCount;
    }

    public void setMultipleChoiceCount(Integer multipleChoiceCount) {
        this.multipleChoiceCount = multipleChoiceCount;
    }

    public Integer getTrueFalseCount() {
        return trueFalseCount;
    }

    public void setTrueFalseCount(Integer trueFalseCount) {
        this.trueFalseCount = trueFalseCount;
    }
}