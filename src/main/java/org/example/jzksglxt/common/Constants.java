package org.example.jzksglxt.common;

/**
 * 常量类
 */
public class Constants {

    /**
     * 角色常量
     */
    public static class Role {
        /**
         * 管理员
         */
        public static final String ADMIN = "admin";
        /**
         * 教练
         */
        public static final String TEACHER = "teacher";
        /**
         * 学员
         */
        public static final String STUDENT = "student";
    }

    /**
     * 状态常量
     */
    public static class Status {
        /**
         * 启用
         */
        public static final String ACTIVE = "active";
        /**
         * 禁用
         */
        public static final String INACTIVE = "inactive";
    }

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 分页默认值
     */
    public static class Page {
        /**
         * 默认页码
         */
        public static final int DEFAULT_PAGE_NUM = 1;
        /**
         * 默认每页条数
         */
        public static final int DEFAULT_PAGE_SIZE = 10;
    }
}