package top.mxzero.model;

import lombok.Data;
import java.util.Date;

@Data
public class Comment {
    private Long id;
    private String content;
    private Long bookId;
    private Long orderId;
    private User user;
    private Date createTime;
    private Float score;
}
