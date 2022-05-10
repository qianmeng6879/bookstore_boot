package top.mxzero.common.exception;

public class OSSResourceNotFoundException extends CustomException{
    public OSSResourceNotFoundException() {
        super("OSS资源不存在");
    }
}
