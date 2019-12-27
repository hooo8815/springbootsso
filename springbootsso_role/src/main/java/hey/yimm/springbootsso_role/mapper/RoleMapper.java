package hey.yimm.springbootsso_role.mapper;

import hey.yimm.springbootsso_role.bean.Role;

import java.util.Set;

public interface RoleMapper {

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}