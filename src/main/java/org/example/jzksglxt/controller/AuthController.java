package org.example.jzksglxt.controller;

import org.example.jzksglxt.common.ErrorCode;
import org.example.jzksglxt.common.ResponseResult;
import org.example.jzksglxt.dto.LoginRequest;
import org.example.jzksglxt.entity.SysUser;
import org.example.jzksglxt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseResult<SysUser> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        
        Optional<SysUser> userOptional = sysUserService.login(username, password);
        if (userOptional.isPresent()) {
            return ResponseResult.success(userOptional.get(), "登录成功");
        } else {
            return ResponseResult.error(ErrorCode.USERNAME_PASSWORD_ERROR);
        }
    }

    /**
     * 用户退出
     * @return 退出结果
     */
    @PostMapping("/logout")
    public ResponseResult<Void> logout() {
        // 这里可以添加退出逻辑，比如清除token等
        return ResponseResult.success("退出成功");
    }
}