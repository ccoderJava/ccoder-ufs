package cc.ccoder.ufs.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cc.ccoder.ufs.oss.qiniu.factory.QiniuOssClientFactory;
import cc.ccoder.ufs.oss.qiniu.factory.QiniuOssClientFactoryImpl;
import cc.ccoder.ufs.oss.qiniu.properties.QiniuOssProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date QiniuOssConfiguration.java v1.0 2021/11/2 16:27
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "qiniu")
@EnableConfigurationProperties(QiniuOssProperties.class)
public class QiniuOssConfiguration {

    @Bean
    public QiniuOssClientFactory qiniuOssClientFactory(QiniuOssProperties qiniuOssProperties) {
        log.info("Initialize qiniu OSS Client factory");
        return new QiniuOssClientFactoryImpl(qiniuOssProperties);
    }

}
