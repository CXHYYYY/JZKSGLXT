package org.example.jzksglxt.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "question_count")
    private Integer questionCount;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "pass_score")
    private Integer passScore;

    @Column(name = "single_choice_count")
    private Integer singleChoiceCount;

    @Column(name = "multiple_choice_count")
    private Integer multipleChoiceCount;

    @Column(name = "true_false_count")
    private Integer trueFalseCount;

    @Column(name = "score_per_question")
    private Integer scorePerQuestion;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "create_time")
    private Date createTime;

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
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

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
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

    @PrePersist
    protected void onCreate() {
        createTime = new Date();
    }
}
