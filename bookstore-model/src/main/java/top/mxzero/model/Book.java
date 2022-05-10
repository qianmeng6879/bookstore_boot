package top.mxzero.model;

import lombok.Data;
import java.util.Date;

@Data
public class Book {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Date publishDate;
    private Double price;
    private String picture;
    private String description;
    private Integer categoryId;
    private Date createTime;
    private Date updateTime;
}
