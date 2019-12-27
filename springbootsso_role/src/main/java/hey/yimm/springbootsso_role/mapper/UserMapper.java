package hey.yimm.springbootsso_role.mapper;

import hey.yimm.springbootsso_role.bean.Role;
import hey.yimm.springbootsso_role.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User selectUserByUsername(String username);

    User selectUserSuperInfoByUsername(String username);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}