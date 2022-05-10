package top.mxzero.common.exception;

public class OrderNotFoundException extends CustomException{
    public OrderNotFoundException(){
        super("订单ID不存在");
    }

}
