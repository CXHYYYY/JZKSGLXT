package org.example.jzksglxt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，添加静态资源映射
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加图片资源映射
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/Users/12833/Desktop/JZKSGLXT/images/");
    }
}
