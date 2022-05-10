package top.mxzero.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVO implements Serializable {
    private Long id;
    private Long userId;
    @NotNull(message = "收获地址编号不能为空--addressId")
    private Long addressId;
    @NotNull(message = "图书商品编号不能为空--bookId")
    private Long bookId;
    private UserVO user;
    private AddressVO address;
    private BookVO book;
    private Double price;
    @NotNull(message = "商品数量不能为空--number")
    private Integer number;
    @NotBlank(message = "支付类型不能为空--payType")
    private String payType;
    private String description;
    private Integer state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
