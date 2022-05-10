package top.mxzero.common.exception;

public class GoodsNotFoundException extends CustomException{
    public GoodsNotFoundException() {
        super("商品未找到。");
    }
}
