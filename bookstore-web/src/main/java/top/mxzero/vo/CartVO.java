package top.mxzero.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CartVO {
    private Long id;
    private Long userId;
    @NotNull(message = "图书商品编号不能为空--bookId")
    private Long bookId;
    private BookVO book;
    @NotNull(message = "商品数量不能为空--count")
    private Integer count;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
