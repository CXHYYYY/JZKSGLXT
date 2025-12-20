package org.example.jzksglxt.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin_operation_log")
public class AdminOperationLog {

    @Id
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private SysUser admin;

    @Column(name = "operation_time", nullable = false)
    private Date operationTime;

    @Column(name = "operation_type", length = 20, nullable = false)
    private String operationType;

    @Column(name = "operation_module", length = 50, nullable = false)
    private String operationModule;

    @Column(name = "operation_detail", columnDefinition = "TEXT", nullable = false)
    private String operationDetail;

    /**
     * Getter for logId
     * @return logId
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * Setter for logId
     * @param logId logId
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * Getter for admin
     * @return admin
     */
    public SysUser getAdmin() {
        return admin;
    }

    /**
     * Setter for admin
     * @param admin admin
     */
    public void setAdmin(SysUser admin) {
        this.admin = admin;
    }

    /**
     * Getter for operationTime
     * @return operationTime
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * Setter for operationTime
     * @param operationTime operationTime
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    /**
     * Getter for operationType
     * @return operationType
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Setter for operationType
     * @param operationType operationType
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * Getter for operationModule
     * @return operationModule
     */
    public String getOperationModule() {
        return operationModule;
    }

    /**
     * Setter for operationModule
     * @param operationModule operationModule
     */
    public void setOperationModule(String operationModule) {
        this.operationModule = operationModule;
    }

    /**
     * Getter for operationDetail
     * @return operationDetail
     */
    public String getOperationDetail() {
        return operationDetail;
    }

    /**
     * Setter for operationDetail
     * @param operationDetail operationDetail
     */
    public void setOperationDetail(String operationDetail) {
        this.operationDetail = operationDetail;
    }

    @PrePersist
    protected void onCreate() {
        operationTime = new Date();
    }
}