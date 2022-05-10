package top.mxzero.model;



import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
    private Long id;
    private Long userId;
    private Long addressId;
    private Long bookId;
    private User user;
    private Address address;
    private Book book;
    private Double price;
    private Integer number;
    private String payType;
    private String description;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}
