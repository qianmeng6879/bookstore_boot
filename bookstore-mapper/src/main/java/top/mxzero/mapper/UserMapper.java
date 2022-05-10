package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.mxzero.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User, Long> {

    @Update("update t_user set password = #{password} where id = #{id}")
    public int updatePassword(@Param("id") Long id, @Param("password") String password);

    public User findInfoAndPasswordByUsername(String username);
}
