package org.example.jzksglxt.service;

import org.example.jzksglxt.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 用户服务接口
 */
public interface SysUserService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户信息
     */
    Optional<SysUser> login(String username, String password);

    /**
     * 分页查询用户列表
     * @param username 用户名（可选）
     * @param role 角色（可选）
     * @param status 状态（可选）
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<SysUser> getUserList(String username, String role, String status, Pageable pageable);

    /**
     * 添加用户
     * @param user 用户信息
     * @return 添加后的用户信息
     */
    SysUser addUser(SysUser user);

    /**
     * 更新用户
     * @param user 用户信息
     * @return 更新后的用户信息
     */
    SysUser updateUser(SysUser user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 重置用户密码
     * @param id 用户ID
     * @param newPassword 新密码
     */
    void resetPassword(Long id, String newPassword);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    Optional<SysUser> getUserById(Long id);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    Optional<SysUser> getUserByUsername(String username);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
}