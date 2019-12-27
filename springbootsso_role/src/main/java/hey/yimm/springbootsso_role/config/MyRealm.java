package hey.yimm.springbootsso_role.config;

import hey.yimm.springbootsso_role.bean.Permission;
import hey.yimm.springbootsso_role.bean.Role;
import hey.yimm.springbootsso_role.bean.User;
import hey.yimm.springbootsso_role.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = ((UsernamePasswordToken) principals.getPrimaryPrincipal()).getUsername();
        User userSuperInfoByUsername = userService.getUserSuperInfoByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<Role> roleSet = userSuperInfoByUsername.getRoleSet();
        Iterator<Role> iterator = roleSet.iterator();
        while (iterator.hasNext()){
            Role role = iterator.next();
            simpleAuthorizationInfo.addRole(role.getName());
            Set<Permission> permissionSet = role.getPermissionSet();
            Iterator<Permission> iterator1 = permissionSet.iterator();
            while (iterator1.hasNext()){
                Permission permission = iterator1.next();
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("AuthenticationToken:"+token.getPrincipal().toString());
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User usersByUsername = userService.getUsersByUsername(username);
        if (usersByUsername==null)
            return null;
        ByteSource bytes = ByteSource.Util.bytes(usernamePasswordToken.getPassword());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(usernamePasswordToken,usersByUsername.getPassword(),bytes,getName());
        return simpleAuthenticationInfo;
    }
}
