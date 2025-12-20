package org.example.jzksglxt.service.impl;

import org.example.jzksglxt.entity.AdminOperationLog;
import org.example.jzksglxt.entity.SysUser;
import org.example.jzksglxt.repository.AdminOperationLogRepository;
import org.example.jzksglxt.repository.SysUserRepository;
import org.example.jzksglxt.service.AdminOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员操作日志服务实现类
 */
@Service
public class AdminOperationLogServiceImpl implements AdminOperationLogService {

    @Autowired
    private AdminOperationLogRepository adminOperationLogRepository;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public void recordLog(Long adminId, String operationType, String operationModule, String operationDetail) {
        // 查询管理员信息
        SysUser admin = sysUserRepository.findById(adminId).orElse(null);
        if (admin != null) {
            // 创建日志记录
            AdminOperationLog log = new AdminOperationLog();
            
            // 生成连续的日志ID
            Long maxLogId = adminOperationLogRepository.findMaxLogId();
            Long newLogId = maxLogId == null ? 1 : maxLogId + 1;
            log.setLogId(newLogId);
            
            log.setAdmin(admin);
            log.setOperationType(operationType);
            log.setOperationModule(operationModule);
            log.setOperationDetail(operationDetail);
            // 保存日志
            adminOperationLogRepository.save(log);
        }
    }

    @Override
    public Page<AdminOperationLog> getLogList(Long adminId, String operationModule, String operationType, Pageable pageable) {
        Specification<AdminOperationLog> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 管理员ID查询
            if (adminId != null) {
                predicates.add(criteriaBuilder.equal(root.get("admin").get("id"), adminId));
            }
            
            // 操作模块查询
            if (operationModule != null && !operationModule.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("operationModule"), operationModule));
            }
            
            // 操作类型查询
            if (operationType != null && !operationType.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("operationType"), operationType));
            }
            
            // 按操作时间倒序
            query.orderBy(criteriaBuilder.desc(root.get("operationTime")));
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
        return adminOperationLogRepository.findAll(specification, pageable);
    }

    @Override
    public void deleteLog(Long logId) {
        adminOperationLogRepository.deleteById(logId);
    }
}