package top.mxzero.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.mxzero.model.Address;

@Mapper
public interface AddressMapper extends BaseMapper<Address, Long> {
}
