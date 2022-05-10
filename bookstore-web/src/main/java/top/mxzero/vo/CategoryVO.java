package top.mxzero.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryVO implements Serializable {
    private Integer id;
    private String name;
    private Integer parentId;
}

