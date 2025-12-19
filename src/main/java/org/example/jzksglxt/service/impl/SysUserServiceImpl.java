package org.example.jzksglxt.service.impl;

import org.example.jzksglxt.entity.SysUser;
import org.example.jzksglxt.repository.SysUserRepository;
import org.example.jzksglxt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public Optional<SysUser> login(String username, String password) {
        Optional<SysUser> userOptional = sysUserRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            SysUser user = userOptional.get();
            // 只有状态为active的管理员才能登录
            if (password.equals(user.getPassword()) && "admin".equals(user.getRole()) && "active".equals(user.getStatus())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<SysUser> getUserList(String username, String role, String status, Pageable pageable) {
        Specification<SysUser> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 用户名模糊查询
            if (username != null && !username.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            
            // 角色精确查询
            if (role != null && !role.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            
            // 状态精确查询
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
        return sysUserRepository.findAll(specification, pageable);
    }

    @Override
    public SysUser addUser(SysUser user) {
        // 直接使用明文密码，不加密
        return sysUserRepository.save(user);
    }

    @Override
    public SysUser updateUser(SysUser user) {
        // 先获取原有用户信息，保留createTime和password等重要字段
        Optional<SysUser> existingUserOptional = sysUserRepository.findById(user.getId());
        if (existingUserOptional.isPresent()) {
            SysUser existingUser = existingUserOptional.get();
            // 保留原有createTime
            user.setCreateTime(existingUser.getCreateTime());
            // 只有当密码不为null且不为空字符串时才更新密码，否则保留原有密码
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            }
        }
        // 直接使用明文密码，不加密
        return sysUserRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        // 删除用户
        sysUserRepository.deleteById(id);
        
        // 重置自增计数器
        // 查询剩余记录中的最大ID
        List<SysUser> remainingUsers = sysUserRepository.findAll();
        Long newAutoIncrementValue;
        if (remainingUsers.isEmpty()) {
            // 如果没有剩余记录，重置为1
            newAutoIncrementValue = 1L;
        } else {
            // 否则，找到最大ID并设置为maxId + 1
            Long maxId = remainingUsers.stream()
                    .mapToLong(SysUser::getId)
                    .max()
                    .orElse(0L);
            newAutoIncrementValue = maxId + 1;
        }
        
        // 重置自增计数器
        sysUserRepository.resetAutoIncrement(newAutoIncrementValue);
    }

    @Override
    public void resetPassword(Long id, String newPassword) {
        Optional<SysUser> userOptional = sysUserRepository.findById(id);
        if (userOptional.isPresent()) {
            SysUser user = userOptional.get();
            user.setPassword(newPassword);
            sysUserRepository.save(user);
        }
    }

    @Override
    public Optional<SysUser> getUserById(Long id) {
        return sysUserRepository.findById(id);
    }

    @Override
    public Optional<SysUser> getUserByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return sysUserRepository.existsByUsername(username);
    }
}