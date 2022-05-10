package top.mxzero.common.dto;

import lombok.Data;

@Data
public class GoodsDTO {
    private Long id;
    private String name;
    private String create;
    private Double price;
    private Integer number;
}
