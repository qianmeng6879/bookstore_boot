package top.mxzero.common.exception;

public class LoginException extends CustomException{
    public LoginException(){
        super("用户名与密码不匹配。");
    }
}
