package top.mxzero.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.common.enums.OrderState;
import top.mxzero.common.exception.CustomException;
import top.mxzero.mapper.OrderMapper;
import top.mxzero.model.Address;
import top.mxzero.model.Book;
import top.mxzero.model.Order;
import top.mxzero.service.*;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AddressService addressService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> currentUserOrders(Long userId) {
        return orderMapper.findAllByUserId(userId);
    }

    @Override
    public List<Order> listByUserId(Long userId) {
        return orderMapper.findAllByUserId(userId);
    }

    @Override
    public Order get(Long id) {
        return orderMapper.findById(id);
    }

    @Override
    public Map<String,Object> list(Integer current, Integer size) {
        return null;
    }

    @Override
    public boolean add(Order order) {
        Long addressId = order.getAddressId();
        Long bookId = order.getBookId();
        Address currentAddress = addressService.get(addressId);
        if(currentAddress == null){
            throw new CustomException("收货地址不存在。");
        }
        Book currentBook = bookService.get(bookId);
        if(currentBook == null){
            throw new CustomException("购买的商品不存在。");
        }
        order.setAddress(currentAddress);
        order.setBook(currentBook);
        order.setPrice(order.getNumber() * currentBook.getPrice());
        return orderMapper.doCreate(order) > 0;
    }

    @Override
    public boolean update(Order model) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean changeOrderState(Long orderId, Long userId, OrderState orderState) {
        Order currentOrder = orderMapper.findById(orderId);
        if(currentOrder == null){
            throw  new RuntimeException("订单编号不存在。");
        }

        // 管理员修改订单状态
        if(userId == 0L){
            return orderMapper.doUpdateState(currentOrder.getId(),orderState.value()) > 0;
        }

        if(currentOrder.getUserId().longValue() != userId.longValue()) {
            throw new RuntimeException("订单编号错误。");
        }

        if(currentOrder.getState().intValue() == orderState.value().intValue()){
            return false;
        }

        return orderMapper.doUpdateState(currentOrder.getId(),orderState.value()) > 0;
    }
}
