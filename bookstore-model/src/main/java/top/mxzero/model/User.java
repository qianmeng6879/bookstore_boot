package top.mxzero.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String mobile;
    private String sex;
    private String picture;
    private Date createTime;
    private Date updateTime;
}
