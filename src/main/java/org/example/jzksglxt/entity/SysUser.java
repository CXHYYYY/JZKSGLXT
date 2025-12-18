package org.example.jzksglxt.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "real_name", length = 50, nullable = false)
    private String realName;

    @Column(name = "role", length = 20, nullable = false)
    private String role;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * Getter for id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for realName
     * @return realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Setter for realName
     * @param realName realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * Getter for role
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role
     * @param role role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Getter for phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for status
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for status
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter for createTime
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter for createTime
     * @param createTime createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter for updateTime
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Setter for updateTime
     * @param updateTime updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @PrePersist
    protected void onCreate() {
        createTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
    }
}