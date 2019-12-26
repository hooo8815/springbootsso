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
        if (httpServletRequest.getRequestURI().contains("login.html")||httpServletRequest.getServletPath().contains("/user/login")||httpServletRequest.getServletPath().contains("css")||httpServletRequest.getServletPath().contains("images")||httpServletRequest.getServletPath().contains("js")||httpServletRequest.getServletPath().contains("layui")||httpServletRequest.getServletPath().contains("treetable-lay")||httpServletRequest.getServletPath().contains("error")){
            System.out.println("放行操作"+httpServletRequest.getRequestURL());
            return true;
        }else{
            String token = httpServletRequest.getParameter("token");
            System.out.println("token:"+token);
            if (token==null||token.equals("")){
                System.out.println(httpServletRequest.getRequestURL()+"无token");
                httpServletResponse.sendRedirect("/login.html");
                return false;
            }
            String usernameByToken = JWTUtil.getUsernameByToken(token);
            System.out.println("usernameByToken:"+usernameByToken);
            User usersByUsername = userService.getUsersByUsername(usernameByToken);
            if (usersByUsername==null) {
                System.out.println("无用户");
                httpServletResponse.sendRedirect("/login.html");
                return false;
            }
            System.out.println("验证成功，放行");
            return true;
        }
    }
}
