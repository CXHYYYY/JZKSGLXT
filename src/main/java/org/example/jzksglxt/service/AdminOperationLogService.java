package org.example.jzksglxt.service;

import org.example.jzksglxt.entity.AdminOperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOperationLogService {

    /**
     * 记录管理员操作日志
     * @param adminId 管理员ID
     * @param operationType 操作类型：create/update/delete/view
     * @param operationModule 操作模块：user_management/question_management/exam_config
     * @param operationDetail 操作详情
     */
    void recordLog(Long adminId, String operationType, String operationModule, String operationDetail);

    /**
     * 获取操作日志列表
     * @param adminId 管理员ID
     * @param operationModule 操作模块
     * @param operationType 操作类型
     * @param pageable 分页参数
     * @return 操作日志分页列表
     */
    Page<AdminOperationLog> getLogList(Long adminId, String operationModule, String operationType, Pageable pageable);

    /**
     * 删除操作日志
     * @param logId 日志ID
     */
    void deleteLog(Long logId);
}