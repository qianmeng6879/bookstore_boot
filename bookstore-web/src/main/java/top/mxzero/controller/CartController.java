package top.mxzero.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.common.ResponseData;
import top.mxzero.model.Cart;
import top.mxzero.service.ShoppingCartService;
import top.mxzero.vo.CartVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    private ShoppingCartService service;


    /**
     * 当前登录添加商品到购物车
     * @param cartVO 购物车VO类
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @PostMapping("/carts")
    public Object add(@Valid @RequestBody CartVO cartVO, HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        cartVO.setUserId(userId);
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartVO,cart);
        boolean result = service.add(cart);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("添加商品到购物车");
        responseData.setResult(result);
        return responseData;
    }

    /**
     * 当前用户的购物车信息
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @GetMapping("/carts")
    public Object list(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        List<Cart> list = service.list(userId);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("购物车数据");
        responseData.setResult(list);
        return responseData;
    }

    /**
     * 用户删除购物车中的信息
     * @param id 购物车信息ID
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @DeleteMapping("/carts/{id}")
    public Object remove(@PathVariable Long id, HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        boolean result = service.removeById(id, userId);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("购物车删除数据");
        responseData.setResult(result);
        return responseData;
    }
}

