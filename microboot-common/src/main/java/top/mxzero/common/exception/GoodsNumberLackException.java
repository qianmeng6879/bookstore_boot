package top.mxzero.common.exception;

public class GoodsNumberLackException extends CustomException{
    public GoodsNumberLackException(){
        super("商品库存不足。");
    }


}
