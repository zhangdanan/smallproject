package sloth.zhang.test.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sloth.zhang.test.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yoga zhang
 * @Type LoginInterceptor.java
 * @date 2020/9/3 18:36
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    //请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {

        User user=(User) request.getSession().getAttribute("user");

        if(null!=user){
            return true;
        }else {
            response.sendRedirect("/index");
            return false;
        }
    }

}
