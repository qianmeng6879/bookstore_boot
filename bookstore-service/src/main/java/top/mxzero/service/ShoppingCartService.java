package top.mxzero.service;

import top.mxzero.model.Cart;

import java.util.List;

public interface ShoppingCartService {

    public List<Cart> list(Long userId);

    /**
     * 根据ID删除购物车数据
     * @param id 购物车信息ID
     * @param userId 用户ID
     * @return 删除成功返回true,否则返回false
     */
    public boolean removeById(Long id, Long userId);

    /**
     * 添加商品到购物车
     * @param shoppingCart 购物车model对象
     * @return 添加成功返回true,否则返回false
     */
    public boolean add(Cart shoppingCart);

    /**
     * 判断用户时候已经将图书加入购物车
     * @param userId 用户ID
     * @param bookId 图书ID
     * @return 已加入返回true，否则返回false
     */
    public boolean hasBook(Long userId, Long bookId);
}
