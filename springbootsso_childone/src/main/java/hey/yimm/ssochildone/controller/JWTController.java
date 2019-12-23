package hey.yimm.ssochildone.controller;

import hey.yimm.ssochildone.entity.User;
import hey.yimm.ssochildone.service.JWTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JWTController {

    @Autowired
    private JWTServiceImpl userService;

    @RequestMapping("/jwtlogin")
    @ResponseBody
    public String login(User user){
        String token = userService.createToken(user);
        return token;
    }

    @RequestMapping("/jwtdeencode")
    @ResponseBody
    public String deEncode(String token){
        String s = userService.getUsernameByToken(token);
        return s;
    }
}
