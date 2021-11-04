package cc.ccoder.ufs.oss.qiniu.factory;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import cc.ccoder.ufs.oss.qiniu.client.QiniuOssClient;
import cc.ccoder.ufs.oss.qiniu.properties.QiniuOssProperties;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date QiniuOssClientFactoryImpl.java v1.0 2021/11/2 16:05
 */
public class QiniuOssClientFactoryImpl implements QiniuOssClientFactory {

    private final QiniuOssProperties qiniuOssProperties;

    public QiniuOssClientFactoryImpl(QiniuOssProperties qiniuOssProperties) {
        this.qiniuOssProperties = qiniuOssProperties;
    }

    @Override
    public QiniuOssClient create() {
        Configuration configuration = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(configuration);
        Auth auth = Auth.create(qiniuOssProperties.getAccessKeyId(), qiniuOssProperties.getAccessKeySecret());
        return QiniuOssClient.builder().uploadManager(uploadManager).auth(auth).build();
    }
}
