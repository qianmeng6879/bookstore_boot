package top.mxzero.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.common.ResponseData;
import top.mxzero.common.dto.UserDTO;
import top.mxzero.common.exception.CustomException;
import top.mxzero.common.exception.UserNotFoundException;
import top.mxzero.model.User;
import top.mxzero.service.RoleService;
import top.mxzero.service.PermissionService;
import top.mxzero.service.UserService;
import top.mxzero.vo.PageVO;
import top.mxzero.vo.PasswordVO;
import top.mxzero.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.util.*;

@Log4j2
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    /**
     * 管理员或工作人员查看用户列表
     * @param pageVO 分页查询参数
     * @return 以统一格式返回数据
     */
    @JWTAuthentication(roles = {"ADMIN","STAFF"}, permission = {"user:view"})
    @GetMapping("/users")
    public Object userList(PageVO pageVO) {
        System.out.println(pageVO);
        Map<String, Object> data = userService.list(pageVO.getCurrent(), pageVO.getSize());
        ResponseData responseData = new ResponseData();
        responseData.setResult(data);
        responseData.setStatus(200);
        responseData.setMessage("用户列表。");
        return responseData;
    }

    /**
     * 查看用户基本信息
     * @param id 用户ID
     * @return 以统一格式返回数九
     */
    @GetMapping("/users/{id}")
    public Object getUser(@PathVariable("id") Long id) {
        User user = userService.get(id);
        if (user == null) {
            throw new UserNotFoundException();
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);

        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setResult(userDTO);
        responseData.setMessage("获取用户信息。");
        return responseData;
    }

    /**
     * 用户注册
     * @param userVO 用户VO类
     * @return 注册成功返回对应数据
     */
    @PostMapping("/users")
    public Object register(@Valid @RequestBody UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        boolean result = userService.add(user);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(201);
        responseData.setMessage("用户注册。");
        responseData.setResult(result);
        return responseData;
    }

    /**
     * 用户登录
     * @param userVO 通过username,password进行认证
     * @return 以统一格式返回数据
     */
    @PostMapping("/login")
    public Object login(@Valid @RequestBody UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        Map<String, Object> result = userService.login(user);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setResult(result);
        responseData.setMessage("用户认证成功。");
        return responseData;
    }

    /**
     * 当前登录用户的详细信息
     * @param request 用于获取当前登录用户的ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @GetMapping("/users/current")
    public Object currentUser(HttpServletRequest request) {
        Long userid = (Long) request.getAttribute("USERID");
        User user = userService.get(userid);
        if (user == null) {
            throw new UserNotFoundException();
        }
        List<String> currentUserRoles = roleService.getCurrentUserRoles(user.getId());
        List<String> currentUserPermissions = permissionService.getCurrentUserPermissions(user.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setPassword(null);
        userVO.setPermissions(currentUserPermissions);
        userVO.setRoles(currentUserRoles);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setMessage("获取当前认证用户的信息。");
        responseData.setResult(userVO);
        return responseData;
    }

    /**
     * 当前登录用户修改信息
     * @param id 用户ID
     * @param userVO 用户修改的VO对象
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @SneakyThrows
    @PutMapping(value = "/users/{id}")
    public Object userUpdate(@PathVariable Long id, @RequestBody UserVO userVO, HttpServletRequest request) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        if (!Objects.equals(id, user.getId())) {
            throw new CustomException("用户ID不一致。");
        }
        Long userId = (Long) request.getAttribute("USERID");
        List<String> roles = (List<String>) request.getAttribute("roles");
        // 是否为修改当前登录用户的信息
        if(userId.equals(id)){
            boolean result = userService.update(user);
            ResponseData responseData = new ResponseData();
            responseData.setMessage("当前用户信息修改。");
            responseData.setResult(result);
            responseData.setStatus(200);
            return responseData;
        }

        if(!(roles.contains("ADMIN") || roles.contains("STAFF"))){
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(404);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ResponseData responseData = new ResponseData();
            responseData.setResult(false);
            responseData.setMessage("操作路径不存在。");
            @Cleanup PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(responseData));
            return null;
        }
        // 工作人员修改用户信息
//        boolean b = userService.update(user, userId, roles);
        boolean result = userService.update(user);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("用户信息修改。");
        responseData.setResult(result);
        responseData.setStatus(200);
        return responseData;
    }

    /**
     * 后台工作人员修改用户密码
     * @param id      被修改密码的用户ID
     * @param password 新的密码
     * @param request  获取后台工作人员的ID
     * @return 修改成功返回对应结果
     */
    @JWTAuthentication(roles = {"ADMIN", "STAFF"}, permission = "user:change")
    @PatchMapping("/users/changePassword")
    public Object changePassword(@RequestParam("id") Long id,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request) {
        Long operatorId = (Long) request.getAttribute("USERID");
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        boolean result = userService.update(user);
        log.info("operator:{},user:{}", operatorId, id);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(201);
        responseData.setMessage("修改用户密码。");
        responseData.setResult(result);
        return responseData;
    }

    /**
     * 管理员删除用户
     * @param id 要删除的用户ID
     * @param request 用于获取管理员ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication(roles = "ADMIN")
    @DeleteMapping("/users/{id}")
    public Object removeUser(@PathVariable("id") Long id, HttpServletRequest request){
        Long operatorId = (Long) request.getAttribute("USERID");
        boolean result = userService.remove(id);
        log.info("delete.user,operator:{},user:{}", operatorId, id);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(204);
        responseData.setMessage("用户删除。");
        responseData.setResult(result);
        return responseData;
    }


    /**
     * 用户密码修改
     * @return 以ResponseData格式返回数据
     */
    @JWTAuthentication
    @PostMapping("/users/password")
    public Object userChangePassword(@Valid @RequestBody PasswordVO passwordVO, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("USERID");
        boolean result = userService.changePassword(passwordVO.getOldPassword(), passwordVO.getNewPassword(), userId);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("用户密码修改。");
        responseData.setResult(result);
        return responseData;
    }
}
