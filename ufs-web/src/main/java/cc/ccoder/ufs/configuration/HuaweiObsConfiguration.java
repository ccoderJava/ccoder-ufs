package cc.ccoder.ufs.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cc.ccoder.ufs.oss.huawei.factory.HuaweiObsClientFactory;
import cc.ccoder.ufs.oss.huawei.factory.HuaweiObsClientFactoryImpl;
import cc.ccoder.ufs.oss.huawei.properties.HuaweiObsProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date HuaweiObsConfiguration.java v1.0 2021/11/4 11:28
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "huawei")
@EnableConfigurationProperties(HuaweiObsProperties.class)
public class HuaweiObsConfiguration {

    @Bean
    public HuaweiObsClientFactory huaweiObsClientFactory(HuaweiObsProperties huaweiObsProperties) {
        return new HuaweiObsClientFactoryImpl(huaweiObsProperties);
    }

}
