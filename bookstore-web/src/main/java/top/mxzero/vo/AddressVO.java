package top.mxzero.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressVO {
    private Long id;
    private Long userId;
    @NotBlank(message = "{address.province.notblank.error}")
    private String province;
    @NotBlank(message = "{address.city.notblank.error}")
    private String city;
    @NotBlank(message = "{address.district.notblank.error}")
    private String district;
    @NotBlank(message = "{address.detail.notblank.error}")
    private String detail;
    @NotBlank(message = "{address.signerName.notblank.error}")
    private String signerName;
    @NotBlank(message = "{address.signerMobile.notblank.error}")
    private String signerMobile;
}
