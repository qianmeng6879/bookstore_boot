package top.mxzero.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserVO implements Serializable {
    private Long id;
    @NotBlank(message = "用户名不能为空。")
    private String username;
    @NotBlank(message = "密码不能为空。")
    private String password;
    @Email
    private String email;
    private String mobile;
    private String nickname;
    private String sex;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    private String picture;

    List<String> roles;
    List<String> permissions;
}

