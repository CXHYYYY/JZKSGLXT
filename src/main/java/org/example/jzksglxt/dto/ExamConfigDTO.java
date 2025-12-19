package org.example.jzksglxt.dto;

public class ExamConfigDTO {
    private String examName;
    private Integer subjectId;
    private Integer duration;
    private Integer questionCount;
    private Integer totalScore;
    private Integer passScore;
    private Integer singleChoiceCount;
    private Integer multipleChoiceCount;
    private Integer trueFalseCount;
    // 每题分值
    private Integer scorePerQuestion;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
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

    public Integer getScorePerQuestion() {
        return scorePerQuestion;
    }

    public void setScorePerQuestion(Integer scorePerQuestion) {
        this.scorePerQuestion = scorePerQuestion;
    }
}
