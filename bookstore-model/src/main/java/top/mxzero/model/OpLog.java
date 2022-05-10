package top.mxzero.model;

import lombok.Data;

import java.util.Date;

@Data
public class OpLog {
    private Integer id;
    private Long userId;
    private String reason;
    private String ip;
    private Date creatTime;
}
