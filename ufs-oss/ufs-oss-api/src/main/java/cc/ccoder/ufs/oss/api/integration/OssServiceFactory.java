package cc.ccoder.ufs.oss.api.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cc.ccoder.common.exception.ErrorException;
import cc.ccoder.common.template.factory.AbstractCodeServiceFactory;
import cc.ccoder.ufs.oss.api.OssService;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date OssServiceFactory.java v1.0 2021/11/1 16:14
 */

@Component
public class OssServiceFactory extends AbstractCodeServiceFactory<OssService> {

    @Value("{oss.provider}")
    private String ossProvider;

    public OssServiceFactory(List<OssService> ossServices) {
        super(ossServices);
    }

    public OssService getService() {
        OssService ossService = getService(ossProvider);
        if (ossService == null) {
            throw new ErrorException("Oss service provider configuration not exists");
        }
        return ossService;
    }

    @Override
    protected String getFactoryName() {
        return "OSS Service Provider Factory";
    }

}
