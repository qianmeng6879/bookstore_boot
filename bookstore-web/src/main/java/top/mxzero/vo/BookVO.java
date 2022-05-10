package top.mxzero.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BookVO {
    private Long id;
    @NotBlank(message = "{book.isbn.notblank.error}")
    @Length(max = 13,min = 13,message = "{book.isbn.length.error}")
    private String isbn;
    @NotBlank(message = "{book.title.notblank.error}")
    private String title;
    @NotBlank(message = "{book.author.notblank.error}")
    private String author;
    @NotBlank(message = "{book.publisher.notblank.error}")
    private String publisher;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date publishDate;
    @NotNull(message = "{book.price.notnull.error}")
    private Double price;
    private String picture;
    private String description;
    @NotNull(message = "{book.categoryId.notnull.error}")
    private Integer categoryId;
}
