package hey.yimm.ssochildone.dao;

import hey.yimm.ssochildone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository <User,Integer> {

    @Query("select id,username,password from user where username = #{username}")
    User getOneByUsername(String username);
}
