package cc.ccoder.ufs.oss.qiniu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * <p></p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date QiniuOssProperties.java v1.0  2021/11/2 16:06
 */
@Data
@Validated
@ConfigurationProperties("oss.qiniu")
public class QiniuOssProperties {

    @NotBlank
    private String accessKeyId;

    @NotBlank
    private String accessKeySecret;
}
