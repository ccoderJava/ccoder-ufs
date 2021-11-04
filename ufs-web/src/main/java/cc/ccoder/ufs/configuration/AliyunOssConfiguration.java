package cc.ccoder.ufs.configuration;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.crypto.SimpleRSAEncryptionMaterials;

import cc.ccoder.common.exception.ErrorException;
import cc.ccoder.common.util.RsaUtil;
import cc.ccoder.ufs.oss.api.domain.RsaKeyPair;
import cc.ccoder.ufs.oss.aliyun.factory.AliyunOssClientFactory;
import cc.ccoder.ufs.oss.aliyun.factory.AliyunOssClientFactoryImpl;
import cc.ccoder.ufs.oss.aliyun.properties.AliyunEncryptKeyProperties;
import cc.ccoder.ufs.oss.aliyun.properties.AliyunOssProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date AliyunOssConfiguration.java v1.0 2021/11/1 15:32
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "aliyun")
@EnableConfigurationProperties({AliyunOssProperties.class, AliyunEncryptKeyProperties.class})
public class AliyunOssConfiguration {

    private static final String KEY_CODE = "KEY_CODE";

    @Bean
    public AliyunOssClientFactory aliyunOssClientFactory(AliyunOssProperties aliyunOssProperties,
        AliyunEncryptKeyProperties encryptKeyProperties) {
        log.info("Initialize aliyun OSS factory");

        SimpleRSAEncryptionMaterials encryptionMaterials;
        String currentKeyCode = encryptKeyProperties.getCurrent();
        if (StringUtils.isNotBlank(currentKeyCode)) {
            // currentKeyCode version exists,enable encrypted client
            KeyPair keyPair = buildKeyPair(encryptKeyProperties.getKeyPairMap().get(currentKeyCode));
            Map<String, String> putKeyDesc = buildKeyDesc(encryptKeyProperties.getCurrent());
            encryptionMaterials = new SimpleRSAEncryptionMaterials(keyPair, putKeyDesc);
            log.info("Loading file encryption key:{}", currentKeyCode);
            encryptKeyProperties.getKeyPairMap().forEach((keyCode, encryptPair) -> {
                if (!StringUtils.equals(keyCode, currentKeyCode)) {
                    // currentCode not equals keyCode, loading history encryption key
                    log.info("Loading history encryption key:{}", keyCode);
                    encryptionMaterials.addKeyPairDescMaterial(buildKeyPair(encryptPair), buildKeyDesc(keyCode));
                }
            });

        }
        return new AliyunOssClientFactoryImpl(aliyunOssProperties, null);

    }

    /**
     * Load the currently used client key description
     * 
     * @param current
     *            currently key code
     * @return return description map
     */
    private Map<String, String> buildKeyDesc(String current) {
        Map<String, String> keyDescMap = new HashMap<>();
        keyDescMap.put(KEY_CODE, current);
        return keyDescMap;
    }

    /**
     * Load the currently used client key pair
     * 
     * @param rsaKeyPair
     *            rsa key pair
     * @return response key pair
     */
    private KeyPair buildKeyPair(RsaKeyPair rsaKeyPair) {
        PrivateKey privateKey;
        PublicKey publicKey;
        try {
            privateKey = RsaUtil.getPrivateKey(rsaKeyPair.getPrivateKey());
            publicKey = RsaUtil.getPublicKey(rsaKeyPair.getPublicKey());
        } catch (InvalidKeySpecException e) {
            throw new ErrorException("Loading key pair configuration exception", e);
        }
        return new KeyPair(publicKey, privateKey);
    }
}
