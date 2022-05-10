package top.mxzero.service;


import top.mxzero.model.Address;

import java.util.List;

public interface AddressService extends BaseService<Address, Long> {
    /**
     * 获取根据用户ID获取收获地址
     * @param current 当前页
     * @param size 每页大小
     * @param userId 用户ID
     * @return 以List形式返回
     */
    public List<Address> list(Integer current, Integer size, Long userId);

    /**
     * 用户删除收获地址
     * @param id 收货地址ID
     * @param userId 用户ID
     * @return 删除成功返回true，否则反正false
     */
    public boolean remove(Long id, Long userId);

    /**
     *  用户修改收获地址信息
     * @param address 收获地址实体对象
     * @param userId 用户ID
     * @return 修改成功返回true,否则返回false
     */
    public boolean edit(Address address, Long userId);
}
