package top.mxzero.common.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String mobile;
    private String picture;
    private String sex;
    private boolean isAdmin;
    private boolean isStaff;
    private boolean isDeleted;
    private Date createTime;
    private List<String> group;
    private List<String> permission;
}
