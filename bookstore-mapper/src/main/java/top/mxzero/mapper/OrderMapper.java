package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mxzero.model.Order;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order, Long>{

    public List<Order> findAllByUserId(Long userId);

    public int doUpdateState(@Param("id") Long id, @Param("state") Integer state);
}
