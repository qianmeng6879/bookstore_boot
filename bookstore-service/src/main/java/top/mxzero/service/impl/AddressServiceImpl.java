package top.mxzero.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mxzero.mapper.AddressMapper;
import top.mxzero.model.Address;
import top.mxzero.service.AddressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> list(Integer current, Integer size, Long userId) {
        return addressMapper.findAll("user_id", userId.toString(), null,null);
    }

    @Override
    public boolean remove(Long id, Long userId) {
        Address address = addressMapper.findById(id);
        if(address == null){
            return false;
        }

        if(address.getUserId().longValue() == userId.longValue()){
            return addressMapper.doRemove(id) > 0;
        }
        return false;
    }

    @Override
    public boolean edit(Address address, Long userId) {
        Address currentAddress = addressMapper.findById(address.getId());
        if(currentAddress.getUserId().longValue() == userId.longValue()){
            return addressMapper.doUpdate(address) > 0;
        }
        return false;
    }

    @Override
    public Address get(Long id) {
        return addressMapper.findById(id);
    }

    @Override
    public Map<String,Object> list(Integer current, Integer size) {
        List<Address> records = addressMapper.findAll(null, null, current, size);
        Map<String,Object> map = new HashMap<>();
        map.put("records", records);
        return map;
    }

    @Override
    public boolean add(Address model) {
        return addressMapper.doCreate(model) > 0;
    }

    @Override
    public boolean update(Address model) {
        return addressMapper.doUpdate(model) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return addressMapper.doRemove(id) > 0;
    }
}
