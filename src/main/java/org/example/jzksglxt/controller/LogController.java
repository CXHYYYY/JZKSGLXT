package org.example.jzksglxt.controller;

import org.example.jzksglxt.common.OperationLogUtil;
import org.example.jzksglxt.common.ResponseResult;
import org.example.jzksglxt.entity.AdminOperationLog;
import org.example.jzksglxt.service.AdminOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理控制器
 */
@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private AdminOperationLogService adminOperationLogService;

    /**
     * 获取操作日志列表
     * @param operationType 操作类型（可选）
     * @param operationModule 操作模块（可选）
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @param adminId 管理员ID（从请求头获取）
     * @return 日志列表
     */
    @GetMapping("/list")
    public ResponseResult<Page<AdminOperationLog>> getLogList(
            @RequestParam(value = "operationType", required = false) String operationType,
            @RequestParam(value = "operationModule", required = false) String operationModule,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        
        // 创建分页请求，按操作时间倒序排序
        Pageable pageable = PageRequest.of(
                pageNum - 1, 
                pageSize, 
                Sort.by(Sort.Direction.DESC, "operationTime")
        );
        
        Page<AdminOperationLog> logPage = adminOperationLogService.getLogList(adminId, operationModule, operationType, pageable);
        
        return ResponseResult.success(logPage);
    }

    /**
     * 删除操作日志
     * @param logId 日志ID
     * @param adminId 管理员ID（从请求头获取）
     * @return 删除结果
     */
    @DeleteMapping("/delete/{logId}")
    public ResponseResult<Void> deleteLog(@PathVariable Long logId,
                                         @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        adminOperationLogService.deleteLog(logId);
        
        return ResponseResult.success("日志删除成功");
    }
}