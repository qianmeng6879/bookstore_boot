package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.common.exception.CustomException;
import top.mxzero.mapper.CartMapper;
import top.mxzero.model.Cart;
import top.mxzero.service.BookService;
import top.mxzero.service.ShoppingCartService;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private CartMapper mapper;

    @Autowired
    private BookService bookService;

    @Override
    public List<Cart> list(Long userId) {
        return mapper.findAllByUserId(userId);
    }

    @Override
    public boolean removeById(Long id, Long userId) {
        return mapper.doRemoveById(id, userId) > 0;
    }

    @Override
    public boolean add(Cart cart) {
        if(hasBook(cart.getUserId(), cart.getBookId())){
            throw new CustomException("用户已经收藏该商品。");
        }

        if(bookService.get(cart.getBookId()) == null){
            throw new CustomException("收藏的商品不存在。");
        }

        return mapper.doCreate(cart) > 0;
    }

    @Override
    public boolean hasBook(Long userId, Long bookId) {
        return mapper.getCount(userId,bookId) > 0;
    }
}
