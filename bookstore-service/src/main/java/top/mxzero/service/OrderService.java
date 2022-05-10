package top.mxzero.service;

import top.mxzero.common.enums.OrderState;
import top.mxzero.model.Order;

import java.util.List;

public interface OrderService extends BaseService<Order, Long> {

    /**
     * 根据用户ID获取全部订单
     * @param uid 用户ID
     * @return 以List封装订单数据
     */
    public List<Order> currentUserOrders(Long uid);

    public List<Order> listByUserId(Long userId);

    /**
     * 修改用户订单状态
     * @param orderId 订单ID
     * @param userId 用户ID
     * @param orderState 修改的状态为
     * @return 修改成功返回true,否则返回false
     */
    public boolean changeOrderState(Long orderId, Long userId, OrderState orderState);
}
