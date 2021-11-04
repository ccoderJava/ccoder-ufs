package cc.ccoder.ufs.oss.huawei.factory;

import com.obs.services.ObsClient;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date HuaweiObsClientFactory.java v1.0 2021/11/4 11:15
 */
public interface HuaweiObsClientFactory {

    /**
     * create huawei obs service client
     * 
     * @return obs client
     */
    ObsClient create();
}
