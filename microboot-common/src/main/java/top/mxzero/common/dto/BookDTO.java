package top.mxzero.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookDTO {
    private Long id;
    private Long isbn;
    private String title;
    private String author;
    private Double price;
    private String publisher;
    private Date publishDate;
    private String picture;
    private CategoryDTO category;
}
