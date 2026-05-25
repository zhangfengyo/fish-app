package com.config;

import com.interceptor.AuthorizationInterceptor;
import com.utils.FileUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.file.Path;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/static/**",
                        "/upload/**",
                        "/output/**",
                        "/admin/**",
                        "/front/**",
                        "/front-pc/**",
                        "/favicon.ico"
                );
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path filesRoot = FileUtil.getFilesRoot();
        String filesLocation = filesRoot.toUri().toString();

        registry.addResourceHandler("/upload/**")
                .addResourceLocations(filesLocation + "upload/");

        registry.addResourceHandler("/output/**")
                .addResourceLocations(filesLocation + "output/");

        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/admin/")
                .setCacheControl(CacheControl.noStore().mustRevalidate());

        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/")
                .setCacheControl(CacheControl.noStore().mustRevalidate());

        registry.addResourceHandler("/front-pc/**")
                .addResourceLocations("classpath:/front-pc/")
                .setCacheControl(CacheControl.noStore().mustRevalidate());

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");

        super.addResourceHandlers(registry);
    }

}
