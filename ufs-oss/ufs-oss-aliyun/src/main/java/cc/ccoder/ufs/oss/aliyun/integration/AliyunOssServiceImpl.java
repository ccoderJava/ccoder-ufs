package cc.ccoder.ufs.oss.aliyun.integration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import cc.ccoder.ufs.common.util.CaseFormatUtil;
import cc.ccoder.ufs.oss.aliyun.factory.AliyunOssClientFactory;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.enums.BucketAccessEnum;
import cc.ccoder.ufs.oss.api.enums.OssProviderEnum;
import cc.ccoder.ufs.oss.api.request.GetObjectRequest;
import cc.ccoder.ufs.oss.api.request.GetObjectUrlRequest;
import cc.ccoder.ufs.oss.api.request.PutObjectRequest;
import cc.ccoder.ufs.oss.api.response.GetObjectUrlResponse;
import cc.ccoder.ufs.oss.api.response.PutObjectResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date AliyunOssServiceImpl.java v1.0 2021/11/1 17:23
 */
@Slf4j
@Service
@ConditionalOnBean(AliyunOssClientFactory.class)
public class AliyunOssServiceImpl implements OssService {

    private static final String HEADER_FORBID_OVERWRITE = "x-oss-forbid-overwrite";

    private final AliyunOssClientFactory aliyunOssClientFactory;

    public AliyunOssServiceImpl(AliyunOssClientFactory aliyunOssClientFactory) {
        this.aliyunOssClientFactory = aliyunOssClientFactory;
    }

    @Override
    public PutObjectResponse putObject(PutObjectRequest request) throws Exception {
        OSS oss = aliyunOssClientFactory.create(false, request.getEncrypt());
        try (InputStream inputStream = new FileInputStream(request.getFile())) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            if (request.getMetaData() != null) {
                request.getMetaData().forEach((key, value) -> objectMetadata
                    .addUserMetadata(CaseFormatUtil.fromLowerCamelToLowerHyphen(key), value));
            }
            if (request.getForbidOverwrite() != null) {
                objectMetadata.setHeader(HEADER_FORBID_OVERWRITE, request.getForbidOverwrite().toString());
            }
            log.info("aliyun oss putObject request bucket:{},ossPath:{}", request.getBucket(), request.getOssPath());
            PutObjectResult putObjectResult =
                oss.putObject(request.getBucket(), request.getOssPath(), inputStream, objectMetadata);
            log.info("aliyun oss putObject response :{}", putObjectResult.getRequestId());
        } finally {
            oss.shutdown();
        }
        return new PutObjectResponse();
    }

    @Override
    public GetObjectUrlResponse getObjectUrl(GetObjectUrlRequest request, String bucketAccess) {
        OSS oss = aliyunOssClientFactory.create(false, Boolean.FALSE);
        Date expiration = new Date(System.currentTimeMillis() + request.getExpireSeconds() * 1000);
        try {
            URL url = oss.generatePresignedUrl(request.getBucket(), request.getOssPath(), expiration);
            String result = url.toString();
            if (BucketAccessEnum.PUBLIC_READ.getCode().equals(bucketAccess)
                || BucketAccessEnum.PUBLIC_READ_WRITE.getCode().equals(bucketAccess)) {
                result = result.substring(0, result.indexOf("?"));
            }
            return GetObjectUrlResponse.builder().url(result).build();
        } finally {
            oss.shutdown();
        }

    }

    @Override
    public void getObject(GetObjectRequest request) throws Exception {
        OSS oss = aliyunOssClientFactory.create(false, Boolean.FALSE);
        try {
            log.info("aliyun oss getObject bucket:{},ossPath:{}", request.getBucket(), request.getOssPath());
            OSSObject ossObject = oss.getObject(request.getBucket(), request.getOssPath());
            log.info("aliyun oss getObject response requestID:{}", ossObject.getRequestId());
            ObjectMetadata objectMetadata = ossObject.getObjectMetadata();

            GetObjectRequest.CallbackContext context = new GetObjectRequest.CallbackContext();
            context.setInputStream(ossObject.getObjectContent());
            context.setRequest(request);
            context.setContentType(objectMetadata.getContentType());
            context.setMetaData(filterUserMetaData(objectMetadata.getUserMetadata()));
            context.setContentDisposition(objectMetadata.getContentDisposition());
            request.getCallback().process(context);
            log.info("aliyun oss getObject callback complete");
        } finally {
            oss.shutdown();
        }
    }

    private Map<String, String> filterUserMetaData(Map<String, String> userMetaData) {
        if (userMetaData == null) {
            return null;
        }
        Map<String, String> metaData = new HashMap<>();
        userMetaData.forEach((key, value) -> {
            String metaKey = CaseFormatUtil.fromLowerHyphenToLowerCamel(key);
            metaData.put(metaKey, value);
        });
        return metaData;
    }

    @Override
    public String getServiceCode() {
        return OssProviderEnum.ALIYUN.getCode();
    }
}
