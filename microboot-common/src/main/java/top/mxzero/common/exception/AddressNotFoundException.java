package top.mxzero.common.exception;

public class AddressNotFoundException extends CustomException{
    public AddressNotFoundException(){
        super("收货地址未找到。");
    }
}
