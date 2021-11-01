package cc.ccoder.ufs.oss.factory;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSEncryptionClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.crypto.SimpleRSAEncryptionMaterials;

import cc.ccoder.common.exception.ErrorException;
import cc.ccoder.ufs.oss.factory.AliyunOssClientFactory;
import cc.ccoder.ufs.oss.properties.AliyunOssProperties;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date AliyunOssClientFactoryImpl.java v1.0 2021/11/1 16:46
 */
public class AliyunOssClientFactoryImpl implements AliyunOssClientFactory {

    private final AliyunOssProperties aliyunOssProperties;

    private final SimpleRSAEncryptionMaterials encryptionMaterials;

    public AliyunOssClientFactoryImpl(AliyunOssProperties aliyunOssProperties,
        SimpleRSAEncryptionMaterials encryptionMaterials) {
        this.aliyunOssProperties = aliyunOssProperties;
        this.encryptionMaterials = encryptionMaterials;
    }

    @Override
    public OSS create(boolean userInner, Boolean encrypt) {
        ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
        configuration.setProtocol(Protocol.HTTPS);
        String endpoint = userInner ? aliyunOssProperties.getInternalEndpoint() : aliyunOssProperties.getEndpoint();
        if (encrypt == null || !encrypt) {
            // not encrypt
            return new OSSClientBuilder().build(endpoint, aliyunOssProperties.getAccessKeyId(),
                aliyunOssProperties.getAccessKeySecret(), configuration);
        } else if (encryptionMaterials != null) {
            // client encrypt config
            return new OSSEncryptionClientBuilder().build(endpoint, aliyunOssProperties.getAccessKeyId(),
                aliyunOssProperties.getAccessKeySecret(), encryptionMaterials);
        } else {
            throw new ErrorException("Encrypt config error");
        }
    }
}
