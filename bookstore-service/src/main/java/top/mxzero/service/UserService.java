package top.mxzero.service;

import top.mxzero.model.User;

import java.util.Map;

public interface UserService extends BaseService<User, Long>{

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 以model对象返回用户信息
     */
    public User getByUsername(String username);

    /**
     * 用户登录认证
     * @param user 包含username,password的数据
     * @return 以Map形式返回，包含一下数据
     * key accessToken 用户认证token
     * key userId 用户的ID值
     * key username 用户名
     * key roles 用户的角色信息
     * key permissions 用户的权限信息
     * key currentTime 认证时间
     */
    public Map<String,Object> login(User user);

    /**
     * 用户修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param useId 用户ID
     * @return 修改成功返回true，否则返回false
     */
    public boolean changePassword(String oldPassword, String newPassword, Long useId);

}
