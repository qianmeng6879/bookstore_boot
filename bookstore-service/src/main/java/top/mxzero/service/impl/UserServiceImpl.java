package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.common.DateUtils;
import top.mxzero.common.PasswordUtils;
import top.mxzero.common.exception.CustomException;
import top.mxzero.common.exception.UserNotFoundException;
import top.mxzero.jwt.service.ITokenService;
import top.mxzero.model.User;
import top.mxzero.mapper.UserMapper;
import top.mxzero.service.PermissionService;
import top.mxzero.service.RoleService;
import top.mxzero.service.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public User get(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public Map<String, Object> list(Integer current, Integer size) {
        List<User> records = userMapper.findAll(null, null, current, size);
        int total = userMapper.getCount(null,null);
        Map<String,Object> map = new HashMap<>();

        map.put("data", records);
        map.put("current", (current == null) ? 0 : current);
        map.put("size", records.size());
        map.put("total", total);
        return map;
    }

    @Override
    public boolean add(User user) {
        return userMapper.doCreate(user) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return userMapper.doRemove(id) > 0;
    }

    @Override
    public boolean update(User user) {
        return userMapper.doUpdate(user) > 0;
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.findOne("username", username);
    }

    @Override
    public Map<String, Object> login(User user) {
        User currentUser = userMapper.findInfoAndPasswordByUsername(user.getUsername());
        if(currentUser == null){
            throw new UserNotFoundException();
        }

        if(!PasswordUtils.checkPassword(user.getPassword(),currentUser.getPassword())){
            throw new CustomException("用户名与密码不匹配。");
        }
        List<String> currentUserRoles = roleService.getCurrentUserRoles(currentUser.getId());
        List<String> currentUserPermissions = permissionService.getCurrentUserPermissions(currentUser.getId());

        Map<String,Object> subject = new HashMap<>();
        subject.put("userId", currentUser.getId());
        subject.put("username", currentUser.getUsername());
        subject.put("nickname", currentUser.getNickname());
        subject.put("roles", currentUserRoles);
        subject.put("permissions", currentUserPermissions);

        subject.put("currentTime", DateUtils.format(new Date()));
        String token = tokenService.createToken(UUID.randomUUID().toString(), subject);
        Map<String,Object> data = new HashMap<>();
        data.put("accessToken", token);
        data.put("username", currentUser.getUsername());
        data.put("roles", currentUserRoles);
        data.put("permissions", currentUserPermissions);
        data.put("userId", currentUser.getId());
        data.put("currentTime", subject.get("currentTime"));
        return data;
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword, Long useId) {
        User currentUser = userMapper.findById(useId);

        // 判断原始密码是否匹配
        if(!PasswordUtils.checkPassword(oldPassword, currentUser.getPassword())){
            throw new CustomException("原始密码错误。");
        }

        User user = new User();
        user.setId(currentUser.getId());
        user.setPassword(PasswordUtils.getSecurityPassword(newPassword));


        return userMapper.doUpdate(user) > 0;
    }
}
