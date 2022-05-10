package top.mxzero.vo;


import lombok.Data;

@Data
public class PageVO {
    private Integer current;
    private Integer size;
    private String column;
    private String keyword;
}
