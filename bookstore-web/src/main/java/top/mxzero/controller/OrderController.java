package top.mxzero.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.common.ResponseData;
import top.mxzero.common.enums.OrderState;
import top.mxzero.model.Order;
import top.mxzero.service.OrderService;
import top.mxzero.vo.OrderVO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 当前登录用户的订单列表
     * @param request 用于获取用户ID
     * @return 以统一格式返回数据
     */
    @JWTAuthentication
    @GetMapping("/orders")
    public Object orderList(HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        List<Order> orders = orderService.currentUserOrders(userId);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setMessage("所有订单信息。");
        responseData.setResult(orders);
        return responseData;
    }

    /**
     * 用户新建订单
     * @param request 获取用户ID
     * @return 以ResponseData封装返回结果
     */
    @JWTAuthentication
    @PostMapping("/orders")
    public Object addOrder(HttpServletRequest request, @Valid @RequestBody OrderVO orderVO){
        Order order = new Order();
        BeanUtils.copyProperties(orderVO,order);
        order.setState(OrderState.CREATED.value());
        Long userid = (Long) request.getAttribute("USERID");
        order.setUserId(userid);
        orderService.add(order);

        ResponseData responseData = new ResponseData();
        responseData.setStatus(201);
        responseData.setMessage("新建订单。");
        responseData.setResult(order);
        return responseData;
    }

    /**
     * 用户或商家查看订单详情信息
     * @param id 订单ID
     * @return 以ResponseData封装订单信息
     * @throws NoHandlerFoundException 订单ID不存在时抛出URL不存在异常
     */
    @JWTAuthentication(roles = {"STAFF","USER"})
    @GetMapping("/orders/{id}")
    public Object oneOrder(@PathVariable("id") Long id) throws NoHandlerFoundException {
        Order order = orderService.get(id);
        if(order == null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            throw new NoHandlerFoundException(request.getMethod(),request.getRequestURI(),new HttpHeaders());
        }
        ResponseData responseData = new ResponseData();
        responseData.setResult(order);
        responseData.setMessage("单个订单详细信息");
        return responseData;
    }

    /**
     * 用户取消订单
     * @param id 订单编号
     * @param request 用户获取用户ID
     * @return 以ResponseData封装返回结果
     */
    @JWTAuthentication
    @PatchMapping("/orders/{id}/cancel")
    public Object cancelOrder(@PathVariable Long id, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("USERID");
        boolean result = orderService.changeOrderState(id, userId, OrderState.CANCELED);
        ResponseData responseData = new ResponseData();
        responseData.setResult(result);
        responseData.setMessage("取消订单");
        return responseData;
    }

    /**
     * 用户签收订单
     * @param id 订单
     * @param request 用于获取用户ID
     * @return 以ResponseData封装返回结果
     */
    @JWTAuthentication
    @PatchMapping("/orders/{id}/sign")
    public Object signedOrder(@PathVariable Long id, HttpServletRequest request){
        Long userId = (Long)request.getAttribute("USERID");
        boolean result = orderService.changeOrderState(id, userId, OrderState.HAS_SIGNED);
        ResponseData responseData = new ResponseData();
        responseData.setResult(result);
        responseData.setMessage("订单签收。");
        return responseData;
    }

    /**
     * 商家发货修改订单状态为已发货
     * @param id 订单ID
     * @return 以ResponseData封装返回结果
     */
    @JWTAuthentication(roles = {"STAFF"}, permission = {"order:change"})
    @PatchMapping("/orders/{id}/send")
    public Object sendOrder(@PathVariable Long id){
        boolean result = orderService.changeOrderState(id, 0L, OrderState.HAS_SEND);
        ResponseData responseData = new ResponseData();
        responseData.setMessage("订单发货。");
        responseData.setResult(result);
        return responseData;
    }
}
