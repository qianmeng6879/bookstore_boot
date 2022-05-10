package top.mxzero.service;


import java.util.List;

public interface PermissionService {
    /**
     * 根据用户ID获取对应的权限信息
     * @param uid 用户ID
     * @return 以List返回权限信息
     */
    public List<String> getCurrentUserPermissions(Long uid);
}
