package sloth.zhang.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import sloth.zhang.test.filter.LoginInterceptor;

/**
 * @author Yoga zhang
 * @Type LoginConfiguration.java
 * @date 2020/9/3 18:38
 */

@Configuration
public class LoginConfiguration {
    @Autowired
    private LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
//添加对用户未登录的拦截器，并添加排除项
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/js/**","/dist/images/**")//排除样式、脚本、图片等资源文件
                .excludePathPatterns("/login")
                .excludePathPatterns("/","/index");

    }

}
