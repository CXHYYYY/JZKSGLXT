package org.example.jzksglxt.controller;

import org.example.jzksglxt.common.Constants;
import org.example.jzksglxt.common.ErrorCode;
import org.example.jzksglxt.common.OperationLogUtil;
import org.example.jzksglxt.common.ResponseResult;
import org.example.jzksglxt.entity.SysUser;
import org.example.jzksglxt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询用户列表
     * @param username 用户名（可选）
     * @param role 角色（可选）
     * @param status 状态（可选）
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @param adminId 管理员ID（从请求头获取）
     * @return 用户列表
     */
    @GetMapping("/list")
    public ResponseResult<Page<SysUser>> getUserList(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        
        // 创建分页请求
        Pageable pageable = PageRequest.of(
                pageNum - 1, 
                pageSize, 
                Sort.by(Sort.Direction.DESC, "id")
        );
        
        Page<SysUser> userPage = sysUserService.getUserList(username, role, status, pageable);
        

        
        return ResponseResult.success(userPage);
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @param adminId 管理员ID（从请求头获取）
     * @return 添加后的用户信息
     */
    @PostMapping
    public ResponseResult<SysUser> addUser(
            @RequestBody SysUser user,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        // 检查用户名是否已存在
        if (sysUserService.existsByUsername(user.getUsername())) {
            return ResponseResult.error(ErrorCode.USERNAME_EXIST);
        }
        
        // 设置默认密码
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(Constants.DEFAULT_PASSWORD);
        }
        
        SysUser newUser = sysUserService.addUser(user);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "create", "user_management", "新增用户[" + user.getUsername() + "]");
        }
        
        return ResponseResult.success(newUser, "用户添加成功");
    }

    /**
     * 更新用户
     * @param user 用户信息
     * @param adminId 管理员ID（从请求头获取）
     * @return 更新后的用户信息
     */
    @PutMapping
    public ResponseResult<SysUser> updateUser(
            @RequestBody SysUser user,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        // 检查用户是否存在
        if (!sysUserService.getUserById(user.getId()).isPresent()) {
            return ResponseResult.error(ErrorCode.USER_NOT_EXIST);
        }
        
        SysUser updatedUser = sysUserService.updateUser(user);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "update", "user_management", "修改用户[" + user.getUsername() + "]");
        }
        
        return ResponseResult.success(updatedUser, "用户更新成功");
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @param adminId 管理员ID（从请求头获取）
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteUser(
            @PathVariable Long id,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        // 检查用户是否存在
        if (!sysUserService.getUserById(id).isPresent()) {
            return ResponseResult.error(ErrorCode.USER_NOT_EXIST);
        }
        
        // 获取用户信息用于日志
        SysUser user = sysUserService.getUserById(id).get();
        
        sysUserService.deleteUser(id);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "delete", "user_management", "删除用户[" + user.getUsername() + "]");
        }
        
        return ResponseResult.success("用户删除成功");
    }

    /**
     * 重置用户密码
     * @param id 用户ID
     * @param newPassword 新密码（可选，默认123456）
     * @param adminId 管理员ID（从请求头获取）
     * @return 重置结果
     */
    @PostMapping("/{id}/resetPassword")
    public ResponseResult<Void> resetPassword(
            @PathVariable Long id,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        
        // 检查用户是否存在
        if (!sysUserService.getUserById(id).isPresent()) {
            return ResponseResult.error(ErrorCode.USER_NOT_EXIST);
        }
        
        // 获取用户信息用于日志
        SysUser user = sysUserService.getUserById(id).get();
        
        // 使用默认密码
        if (newPassword == null || newPassword.isEmpty()) {
            newPassword = Constants.DEFAULT_PASSWORD;
        }
        
        sysUserService.resetPassword(id, newPassword);
        
        // 记录日志
        if (adminId != null) {
            OperationLogUtil.recordLog(adminId, "update", "user_management", "重置用户[" + user.getUsername() + "]密码");
        }
        
        return ResponseResult.success("密码重置成功");
    }

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @param adminId 管理员ID（从请求头获取）
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResponseResult<SysUser> getUserById(
            @PathVariable Long id,
            @RequestHeader(value = "X-Admin-Id", required = false) Long adminId) {
        
        ResponseResult<SysUser> result = sysUserService.getUserById(id)
                .map(ResponseResult::success)
                .orElseGet(() -> ResponseResult.error(ErrorCode.USER_NOT_EXIST));
        

        
        return result;
    }
}