package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mxzero.model.Cart;

import java.util.List;

@Mapper
public interface CartMapper {
    public List<Cart> findAllByUserId(Long userId);

    public int doCreate(Cart shoppingCart);

    public int doRemoveById(Long id, Long userId);

    public int getCount(@Param("userId") Long userId, @Param("bookId")Long bookId);
}
