package cc.ccoder.ufs.oss.qiniu.factory;

import cc.ccoder.ufs.oss.qiniu.client.QiniuOssClient;

/**
 * <p></p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date QiniuOssClientFactory.java v1.0  2021/11/2 16:00
 */
public interface QiniuOssClientFactory {

    QiniuOssClient create();
}
