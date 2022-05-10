package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.mapper.RoleMapper;
import top.mxzero.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getCurrentUserRoles(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }
}
