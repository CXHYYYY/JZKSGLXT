package org.example.jzksglxt.common;

import org.example.jzksglxt.service.AdminOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 操作日志工具类
 */
@Component
public class OperationLogUtil {

    private static AdminOperationLogService adminOperationLogService;

    @Autowired
    public OperationLogUtil(AdminOperationLogService adminOperationLogService) {
        OperationLogUtil.adminOperationLogService = adminOperationLogService;
    }

    /**
     * 记录操作日志
     * @param adminId 管理员ID
     * @param operationType 操作类型：create/update/delete/view
     * @param operationModule 操作模块：user_management/question_management/exam_config
     * @param operationDetail 操作详情
     */
    public static void recordLog(Long adminId, String operationType, String operationModule, String operationDetail) {
        adminOperationLogService.recordLog(adminId, operationType, operationModule, operationDetail);
    }
}