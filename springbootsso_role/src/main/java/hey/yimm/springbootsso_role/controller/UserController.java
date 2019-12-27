package hey.yimm.springbootsso_role.controller;

import hey.yimm.springbootsso_role.bean.User;
import hey.yimm.springbootsso_role.service.UserService;
import hey.yimm.springbootsso_role.util.JWTUtil;
import hey.yimm.springbootsso_role.util.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseData<String> userLogin(@RequestBody User user){
        System.out.println("UserController:"+user.getUsername());
        System.out.println("UserController:"+user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        ResponseData<String> stringResponseData = new ResponseData<>();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        User userByUsernameAndPwd = userService.getUserByUsernameAndPwd(user.getUsername(),user.getPassword());
        try {
            subject.login(usernamePasswordToken);
            String token = JWTUtil.createToken(userByUsernameAndPwd);
            stringResponseData.setCode(0);
            stringResponseData.setT(token);
            stringResponseData.setMessage("成功登录");
            return stringResponseData;
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



//    @RequestMapping("/token")
//    @ResponseBody
//    public ResponseData<String> getUser(HttpServletRequest req,HttpServletResponse resp){
//        stringResponseData.setCode(0);
//        stringResponseData.setMessage("检查token存在");
//        stringResponseData.setT(stringResponseData.getT());
//        return stringResponseData;
//    }


//    @RequiresRoles("超级管理员")
//    @RequiresPermissions("新增")
//    @RequestMapping("/saveinfo")
//    public ResponseData<String> saveInfo(String username,String email,String phone,String realName,String sex,Byte status){
//        System.out.println(username);
//        System.out.println(email);
//        ResponseData<String> stringResponseData = new ResponseData<>();
//        System.out.println("添加用户");
//
//        stringResponseData.setCode(401002);
//        stringResponseData.setMessage("成功登录");
//        return stringResponseData;
//    }


}
