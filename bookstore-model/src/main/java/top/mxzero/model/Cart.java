package top.mxzero.model;

import lombok.Data;
import java.util.Date;

@Data
public class Cart {
    private Long id;
    private Long userId;
    private Long bookId;
    private Book book;
    private Integer count;
    private Date createTime;
}
