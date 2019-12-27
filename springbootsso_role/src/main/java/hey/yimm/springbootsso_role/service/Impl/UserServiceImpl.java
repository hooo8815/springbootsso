package hey.yimm.springbootsso_role.service.Impl;

import hey.yimm.springbootsso_role.bean.User;
import hey.yimm.springbootsso_role.mapper.UserMapper;
import hey.yimm.springbootsso_role.service.UserService;
import hey.yimm.springbootsso_role.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUserByUsernameAndPwd(String username, String password) {
        User user = userMapper.selectUserByUsername(username);
        if (user==null){
            return null;
        }
        if (PasswordUtil.checkPassword(password,user.getPassword())){
            return user;
        }
        return null;
    }

    @Override
    public User getUsersByUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        if (user==null){
            return null;
        }
        return user;
    }

    @Override
    public User getUserSuperInfoByUsername(String username) {
        User user = userMapper.selectUserSuperInfoByUsername(username);
        if (user==null){
            return null;
        }
        return user;
    }
}
