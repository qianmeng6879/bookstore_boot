package top.mxzero.common.enums;

public enum OrderState {
    CANCELED(0,"已取消"),
    CREATED(1,"已创建"),
    NO_PAID(2,"未支付"),
    HAS_PAID(3,"已支付"),
    NO_SEND(4,"未发货"),
    HAS_SEND(5,"已发货"),
    HAS_SIGNED(6, "已签收");

    private final Integer state;
    private final String name;

    private OrderState(Integer state, String name){
        this.state = state;
        this.name = name;
    }

    public Integer value(){
        return this.state;
    }

    public String getName(){
        return this.name;
    }


}
