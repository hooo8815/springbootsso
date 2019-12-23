package hey.yimm.ssochildone.interceptor;

import hey.yimm.ssochildone.dao.UserDao;
import hey.yimm.ssochildone.entity.User;
import hey.yimm.ssochildone.service.JWTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTServiceImpl userService;
    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        if (token==null||token.equals("")){
            return false;
        }
        String usernameByToken = userService.getUsernameByToken(token);
        User oneByUsername = userDao.getOneByUsername(usernameByToken);
        System.out.println(oneByUsername);


        return false;
    }
}
