package hey.yimm.springbootsso_role.controller;

import hey.yimm.springbootsso_role.bean.User;
import hey.yimm.springbootsso_role.service.UserService;
import hey.yimm.springbootsso_role.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public String userLogin(String username,String password){
        System.out.println("UserController:"+username);
        System.out.println("UserController:"+password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        User userByUsernameAndPwd = userService.getUserByUsernameAndPwd(username, password);
        try {
           subject.login(usernamePasswordToken);
            String token = JWTUtil.createToken(userByUsernameAndPwd);
            return token;
        }catch ( UnknownAccountException uae ) {
           //未知账户异常   用户名不存在
           System.out.println(uae.getMessage());
       } catch ( IncorrectCredentialsException ice ) {
           //密码无法匹配异常
           System.out.println(ice.getMessage());
       } catch ( LockedAccountException lae ) {
           //账户锁定异常
           System.out.println(lae.getMessage());
       } catch ( ExcessiveAttemptsException eae ) {
           //错误次数过多异常
           System.out.println(eae.getMessage());
       } catch ( AuthenticationException ae ) {
           //认证错误异常
           System.out.println(ae.getMessage());
       }
        return null;
    }



//    @RequestMapping("/getuser")
//    @ResponseBody
//    public String getUser(String id){
//        User userById = userService.getUserById(id);
//        return userById.getUsername();
//    }


}
