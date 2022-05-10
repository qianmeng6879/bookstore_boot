package top.mxzero.service;


import java.util.List;

public interface RoleService {

    /**
     * 根据用户ID获取对应的角色信息
     * @param uid 用户ID
     * @return 以List返回角色信息
     */
    public List<String> getCurrentUserRoles(Long uid);
}
