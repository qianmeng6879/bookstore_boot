package top.mxzero.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mxzero.common.ResponseData;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.model.Address;
import top.mxzero.service.AddressService;
import top.mxzero.vo.AddressVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 当前登录用户的收获地址列表信息
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @GetMapping("/address")
    public Object currentUserAddress(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("USERID");
        List<Address> currentUserAddress = addressService.list(null,null,userId);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setResult(currentUserAddress);
        responseData.setMessage("当前登录用户的收获地址信息。");
        return responseData;
    }

    /**
     * 用户新增收获地址信息
     * @param request 用于获取用户ID
     * @param addressVO 收获地址VO类
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @PostMapping("/address")
    public Object createAddress(HttpServletRequest request, @Valid @RequestBody AddressVO addressVO){
        Long userId = (Long)request.getAttribute("USERID");
        Address address = new Address();
        BeanUtils.copyProperties(addressVO, address);
        address.setUserId(userId);
        addressService.add(address);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(201);
        responseData.setResult(address);
        responseData.setMessage("新增收获地址信息。");
        return responseData;
    }

    /**
     * 用户删除收获地址信息
     * @param id 要删除的收获地址ID
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @DeleteMapping("/address/{id}")
    public Object removeAddress(@PathVariable Long id, HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        boolean result = addressService.remove(id, userId);
        ResponseData data = new ResponseData();
        data.setMessage("删除收货地址");
        data.setResult(result);
        return data;
    }

    /**
     * 用户修改一条收获地址信息
     * @param id 收获地址ID
     * @param addressVO 收获地址VO类
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @PutMapping("/address/{id}")
    public Object editAddress(@PathVariable Long id, @RequestBody AddressVO addressVO, HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        Address address = new Address();
        BeanUtils.copyProperties(addressVO, address);
        address.setId(id);
        address.setUserId(userId);
        boolean result = addressService.update(address);
        ResponseData data = new ResponseData();
        data.setMessage("修改收获地址信息");
        data.setResult(result);
        return data;
    }

}
