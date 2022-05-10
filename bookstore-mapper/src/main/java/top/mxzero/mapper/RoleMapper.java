package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("select name from auth_role where id in (select role_id from auth_user_role where user_id = #{userId});")
    public List<String> getRoleByUserId(Long userId);
}
