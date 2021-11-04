package cc.ccoder.ufs.oss.huawei.factory;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.BucketLoggingConfiguration;
import com.obs.services.model.HttpProtocolTypeEnum;

import cc.ccoder.ufs.oss.huawei.properties.HuaweiObsProperties;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date HuaweiObsClientFactoryImpl.java v1.0 2021/11/4 11:18
 */
public class HuaweiObsClientFactoryImpl implements HuaweiObsClientFactory {

    private final HuaweiObsProperties huaweiObsProperties;

    public HuaweiObsClientFactoryImpl(HuaweiObsProperties huaweiObsProperties) {
        this.huaweiObsProperties = huaweiObsProperties;
    }

    @Override
    public ObsClient create() {
        ObsConfiguration obsConfiguration = new ObsConfiguration();
        obsConfiguration.setHttpProtocolType(HttpProtocolTypeEnum.HTTP2_0);
        obsConfiguration.setEndPoint(huaweiObsProperties.getEndpoint());
        ObsClient obsClient = new ObsClient(huaweiObsProperties.getAccessKeyId(), huaweiObsProperties.getAccessKeySecret(),
                obsConfiguration);
        BucketLoggingConfiguration bucketLoggingConfiguration = new BucketLoggingConfiguration();

        return obsClient;
    }
}
