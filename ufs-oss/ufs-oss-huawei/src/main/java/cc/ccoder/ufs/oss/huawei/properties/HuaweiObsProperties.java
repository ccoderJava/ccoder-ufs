package cc.ccoder.ufs.oss.huawei.properties;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date HuaweiObsProperties.java v1.0 2021/11/4 11:09
 */
@Data
@Validated
@ConfigurationProperties("oss.huawei")
public class HuaweiObsProperties {

    @NotBlank
    private String accessKeyId;

    @NotBlank
    private String accessKeySecret;

    @NotBlank
    private String endpoint;
}
