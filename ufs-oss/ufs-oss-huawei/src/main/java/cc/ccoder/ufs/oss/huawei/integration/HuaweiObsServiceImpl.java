package cc.ccoder.ufs.oss.huawei.integration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.obs.services.ObsClient;
import com.obs.services.model.*;

import cc.ccoder.ufs.common.util.CaseFormatUtil;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.enums.OssProviderEnum;
import cc.ccoder.ufs.oss.api.request.GetObjectRequest;
import cc.ccoder.ufs.oss.api.request.GetObjectUrlRequest;
import cc.ccoder.ufs.oss.api.request.PutObjectRequest;
import cc.ccoder.ufs.oss.api.response.GetObjectUrlResponse;
import cc.ccoder.ufs.oss.api.response.PutObjectResponse;
import cc.ccoder.ufs.oss.huawei.factory.HuaweiObsClientFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date HuaweiObsServiceImpl.java v1.0 2021/11/4 14:02
 */
@Slf4j
@Service
@ConditionalOnBean(HuaweiObsClientFactory.class)
public class HuaweiObsServiceImpl implements OssService {

    private static final String META_PREFIX = "x-obs-meta-";

    private final HuaweiObsClientFactory huaweiObsClientFactory;

    public HuaweiObsServiceImpl(HuaweiObsClientFactory huaweiObsClientFactory) {
        this.huaweiObsClientFactory = huaweiObsClientFactory;
    }

    @Override
    public PutObjectResponse putObject(PutObjectRequest request) throws Exception {
        try (ObsClient obsClient = huaweiObsClientFactory.create()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            if (request.getMetaData() != null) {
                objectMetadata.setUserMetadata(buildUserMeta(request.getMetaData()));
            }
            // set bucket logging configuration
            obsClient.setBucketLogging(request.getBucket(), new BucketLoggingConfiguration());
            PutObjectResult putObjectResult =
                obsClient.putObject(request.getBucket(), request.getOssPath(), request.getFile(), objectMetadata);
            log.info("huawei obs putObject response: requestId{}", putObjectResult.getRequestId());
        }
        return new PutObjectResponse();
    }

    private Map<String, Object> buildUserMeta(Map<String, String> metaData) {
        Map<String, Object> userMetaData = new HashMap<>();
        metaData.forEach((key, value) -> {
            String metaKey = META_PREFIX + CaseFormatUtil.fromLowerCamelToLowerHyphen(key);
            userMetaData.put(metaKey, value);
        });
        return userMetaData;
    }

    @Override
    public GetObjectUrlResponse getObjectUrl(GetObjectUrlRequest request, String bucketAccess) throws Exception {
        try (ObsClient obsClient = huaweiObsClientFactory.create()) {
            TemporarySignatureRequest signatureRequest =
                new TemporarySignatureRequest(HttpMethodEnum.GET, request.getExpireSeconds());
            signatureRequest.setBucketName(request.getBucket());
            signatureRequest.setObjectKey(request.getOssPath());
            log.info("huawei oss getObjectUrl request bucket:{},ossPath:{}", request.getBucket(), request.getOssPath());
            TemporarySignatureResponse signatureResponse = obsClient.createTemporarySignature(signatureRequest);
            return GetObjectUrlResponse.builder().url(signatureResponse.getSignedUrl()).build();
        }
    }

    @Override
    public void getObject(GetObjectRequest request) throws Exception {
        try (ObsClient obsClient = huaweiObsClientFactory.create()) {
            log.info("huawei obs getObject bucket:{},ossPath:{}", request.getBucket(), request.getOssPath());
            ObsObject obsObject = obsClient.getObject(request.getBucket(), request.getOssPath());
            GetObjectRequest.CallbackContext context = new GetObjectRequest.CallbackContext();
            context.setInputStream(obsObject.getObjectContent());
            context.setRequest(request);
            context.setMetaData(filterResultMeta(obsObject.getMetadata().getAllMetadata()));
            context.setContentType(obsObject.getMetadata().getContentType());
            context.setContentDisposition(obsObject.getMetadata().getContentDisposition());
            request.getCallback().process(context);
            log.info("huawei obs getObject callback complete");
        }
    }

    private Map<String, String> filterResultMeta(Map<String, Object> allMetadata) {
        if (allMetadata == null) {
            return null;
        }
        Map<String, String> userMeta = new HashMap<>();
        allMetadata.forEach((key, value) -> {
            String metaKey = CaseFormatUtil.fromLowerHyphenToLowerCamel(key);
            userMeta.put(metaKey, value.toString());
        });
        return userMeta;
    }

    @Override
    public String getServiceCode() {
        return OssProviderEnum.HUAWEI.getCode();
    }

}
