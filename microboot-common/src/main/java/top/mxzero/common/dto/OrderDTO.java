package top.mxzero.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String name;
    private Date createTime;
    private Integer number;
    private Double detailPrice;
    private String payType;
    private String description;
    private Integer state;
    private UserDTO user;
    private GoodsDTO goods;
    private AddressDTO address;
}
