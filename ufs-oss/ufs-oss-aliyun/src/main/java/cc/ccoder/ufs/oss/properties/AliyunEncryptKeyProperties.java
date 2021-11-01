package cc.ccoder.ufs.oss.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import cc.ccoder.ufs.oss.api.domain.RsaKeyPair;
import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date AliyunEncryptKeyProperties.java v1.0 2021/11/1 16:54
 */
@Data
@ConfigurationProperties("encrypt-key")
public class AliyunEncryptKeyProperties {

    private String current;

    private Map<String, RsaKeyPair> keyPairMap;

}
