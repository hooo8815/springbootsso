package hey.yimm.springbootsso_role.controller;

import com.alibaba.fastjson.JSONObject;
import hey.yimm.springbootsso_role.bean.Role;
import hey.yimm.springbootsso_role.bean.User;
import hey.yimm.springbootsso_role.service.UserService;
import hey.yimm.springbootsso_role.util.JWTUtil;
import hey.yimm.springbootsso_role.util.PasswordUtil;
import hey.yimm.springbootsso_role.util.RedisUtil;
import hey.yimm.springbootsso_role.util.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private RedisUtil redisUtil;

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
            redisUtil.addTokenKey(token,user.getUsername());
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
//    @RequiresPermissions("User:*")
    @RequestMapping("/saveinfo")
    @ResponseBody
    public ResponseData<String> saveInfo(@RequestBody JSONObject jsonObject){
        ResponseData<String> stringResponseData = new ResponseData<>();
        stringResponseData.setCode(0);
        stringResponseData.setMessage("修改失败");
//        System.out.println(jsonObject.toJSONString());
//        System.out.println(jsonObject.getString("username"));
//        System.out.println(jsonObject.getString("email"));
//        System.out.println(jsonObject.getString("phone"));
//        System.out.println(jsonObject.getString("realName"));
//        System.out.println(jsonObject.getString("sex"));
//        System.out.println(jsonObject.getString("status"));
//        User userSuperInfoByUsername = userService.getUserSuperInfoByUsername(user.getUsername());
//        if (userSuperInfoByUsername!=null&&userSuperInfoByUsername.getRoleSet().size()!=0){
//            for (Role role : userSuperInfoByUsername.getRoleSet()) {
//                System.out.println(role);
//            }
//        }
        Session session = SecurityUtils.getSubject().getSession();
        Object currentuser = session.getAttribute("currentuser");
        System.out.println(currentuser);
        if (currentuser!=null&&currentuser!=""){
            User usersByUsername = userService.getUsersByUsername(currentuser.toString());
            usersByUsername.setUsername(jsonObject.getString("username"));
            usersByUsername.setEmail(jsonObject.getString("email"));
            usersByUsername.setPhone(jsonObject.getString("phone"));
            usersByUsername.setRealName(jsonObject.getString("realName"));
            usersByUsername.setSex(new Byte(jsonObject.getString("sex")));
            usersByUsername.setStatus(new Byte(jsonObject.getString("status")));
            userService.changeUserInfo(usersByUsername);
            stringResponseData.setMessage("修改成功");
        }


        System.out.println("修改用户");
        stringResponseData.setMessage("登陆信息失效，请退出重新登陆");

        return stringResponseData;
    }



    @RequestMapping("/pwd")
    @ResponseBody
    public ResponseData<String> changPwd(@RequestBody JSONObject jsonObject){
        ResponseData<String> stringResponseData = new ResponseData<>();
        stringResponseData.setCode(0);
        stringResponseData.setMessage("修改失败");
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.getString("oldPwd"));
        System.out.println(jsonObject.getString("newPwd"));
        System.out.println(jsonObject.getString("rePass"));
        Session session = SecurityUtils.getSubject().getSession();
        Object currentuser = session.getAttribute("currentuser");
        System.out.println(currentuser);
        if (currentuser!=null&&currentuser!=""){
            User usersByUsername = userService.getUsersByUsername(currentuser.toString());
            System.out.println("-----id-------"+usersByUsername.getId());
            if(PasswordUtil.checkPassword(jsonObject.getString("oldPwd"),usersByUsername.getPassword())){
                usersByUsername.setPassword(jsonObject.getString("newPwd"));
                userService.changeUserInfo(usersByUsername);
                stringResponseData.setMessage("修改成功，请重新登陆");
                System.out.println("-------修改成功---------");
            }
        }
        System.out.println("-------修改失败---------");
        stringResponseData.setMessage("登陆信息失效，请退出重新登陆");
        return stringResponseData;
    }


    @RequestMapping("/logout")
    @ResponseBody
    public ResponseData<String> toLogout(){
        ResponseData<String> stringResponseData = new ResponseData<>();
        stringResponseData.setCode(0);
        stringResponseData.setMessage("注销");
        return stringResponseData;
    }




}
