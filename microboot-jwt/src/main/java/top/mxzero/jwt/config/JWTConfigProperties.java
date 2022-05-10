package top.mxzero.jwt.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mxzero.config.jwt")
public class JWTConfigProperties {
    private String sign;
    private String issuer;
    private String secret;
    private String site;
    private long expire;
}
