package cc.ccoder.ufs.oss.aliyun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * <p></p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date AliyunProperties.java v1.0  2021/11/1 15:35
 */
@Data
@Validated
@ConfigurationProperties("oss.aliyun")
public class AliyunOssProperties {

    @NotBlank
    private String accessKeyId;

    @NotBlank
    private String accessKeySecret;

    @NotBlank
    private String endpoint;

    @NotBlank
    private String internalEndpoint;

}
