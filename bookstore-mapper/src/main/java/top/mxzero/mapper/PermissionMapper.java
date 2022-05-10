package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {


    @Select("select name from auth_permission where id in (select permission_id from auth_role_permission where role_id in (select id from auth_user_role where user_id = #{userId}));")
    public List<String> getPermissionByUserid(Long userId);
}
