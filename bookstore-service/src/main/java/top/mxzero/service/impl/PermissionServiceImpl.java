package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.mapper.PermissionMapper;
import top.mxzero.service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> getCurrentUserPermissions(Long uid) {
        return permissionMapper.getPermissionByUserid(uid);
    }
}
