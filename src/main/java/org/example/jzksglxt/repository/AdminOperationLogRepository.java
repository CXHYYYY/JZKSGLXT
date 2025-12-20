package org.example.jzksglxt.repository;

import org.example.jzksglxt.entity.AdminOperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AdminOperationLogRepository extends JpaRepository<AdminOperationLog, Long>, JpaSpecificationExecutor<AdminOperationLog> {
    /**
     * 查询最大的日志ID
     * @return 最大的日志ID，如果没有记录则返回null
     */
    @Query(value = "SELECT COALESCE(MAX(log_id), 0) FROM admin_operation_log", nativeQuery = true)
    Long findMaxLogId();
}