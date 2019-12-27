package hey.yimm.springbootsso_role.service;

import hey.yimm.springbootsso_role.bean.User;


public interface UserService {

    User getUserByUsernameAndPwd(String username, String password);

    User getUsersByUsername(String username);

    User getUserSuperInfoByUsername(String username);
}
