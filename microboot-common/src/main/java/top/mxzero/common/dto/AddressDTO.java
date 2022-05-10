package top.mxzero.common.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String province;
    private String city;
    private String district;
    private String detail;
    private String signerName;
    private String signerMobile;
}
