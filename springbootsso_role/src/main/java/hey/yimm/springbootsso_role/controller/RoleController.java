package hey.yimm.springbootsso_role.controller;

import com.alibaba.fastjson.JSON;
import hey.yimm.springbootsso_role.bean.Role;
import hey.yimm.springbootsso_role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;



}
