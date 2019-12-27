package hey.yimm.springbootsso_role.interceptor;

import hey.yimm.springbootsso_role.bean.User;
import hey.yimm.springbootsso_role.service.UserService;
import hey.yimm.springbootsso_role.util.JWTUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends BasicHttpAuthenticationFilter {

    @Autowired
    private UserService userService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        System.out.println(httpServletRequest.getRequestURL()+"进入拦截器-------------");
        //||httpServletRequest.getServletPath().contains("/user/login")||httpServletRequest.getRequestURI().contains("css")||httpServletRequest.getRequestURI().contains("images")||httpServletRequest.getRequestURI().contains("js")||httpServletRequest.getRequestURI().contains("layui")||httpServletRequest.getRequestURI().contains("treetable-lay")||httpServletRequest.getRequestURI().contains("error")
        if (httpServletRequest.getRequestURI().contains("login.html")){
            System.out.println("放行操作"+httpServletRequest.getRequestURL());
            return true;
        }else{
            String token = httpServletRequest.getHeader("authorization");
            System.out.println("authorization:"+token);
            if (token==null||token.equals("")||token.equals("null")){
                System.out.println(httpServletRequest.getRequestURL()+"无token");
//                httpServletResponse.sendRedirect("/login.html");
                return false;
            }

            String usernameByToken = JWTUtil.getUsernameByToken(token);
            System.out.println("usernameByToken:"+usernameByToken);
            User usersByUsername = userService.getUsersByUsername(usernameByToken);
            if (usersByUsername==null) {
                System.out.println("无用户");
                //httpServletResponse.sendRedirect("/login.html");
                return false;
            }
            System.out.println("验证成功，放行");
            return true;
        }
    }
}
