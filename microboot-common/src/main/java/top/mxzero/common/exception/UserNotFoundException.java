package top.mxzero.common.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(){
        super("用户不存在。");
    }

}
