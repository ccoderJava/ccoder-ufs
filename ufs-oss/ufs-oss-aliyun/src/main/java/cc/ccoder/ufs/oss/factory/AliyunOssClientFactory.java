package cc.ccoder.ufs.oss.factory;

import com.aliyun.oss.OSS;

/**
 * <p>The factory interface to generate Aliyun Oss client</p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date AliyunOssClientFactory.java v1.0  2021/11/1 16:08
 */
public interface AliyunOssClientFactory {

    /**
     * Create Aliyun Oss client
     *
     * @param userInner Use intranet address
     * @param encrypt   whether to generate encrypted client,it can be null, default not encrypted
     * @return aliyun oss client
     */
    OSS create(boolean userInner, Boolean encrypt);
}
