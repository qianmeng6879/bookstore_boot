package top.mxzero.model;


import lombok.Data;


@Data
public class Address {
    private Long id;
    private Long userId;
    private String province;
    private String city;
    private String district;
    private String detail;
    private String signerName;
    private String signerMobile;
}
