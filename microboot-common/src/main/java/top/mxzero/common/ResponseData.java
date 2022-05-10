package top.mxzero.common;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ResponseData {
    private int status = 200;
    private String message;
    private Object result;
    private String errorMessage;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    public ResponseData(){
        this.time = new Date();
    }
}
