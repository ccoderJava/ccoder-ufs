package cc.ccoder.ufs.oss.qiniu.integration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.http.Headers;
import com.qiniu.http.Response;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import cc.ccoder.common.exception.ErrorException;
import cc.ccoder.ufs.common.util.CaseFormatUtil;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.enums.OssProviderEnum;
import cc.ccoder.ufs.oss.api.request.GetObjectRequest;
import cc.ccoder.ufs.oss.api.request.GetObjectUrlRequest;
import cc.ccoder.ufs.oss.api.request.PutObjectRequest;
import cc.ccoder.ufs.oss.api.response.GetObjectUrlResponse;
import cc.ccoder.ufs.oss.api.response.PutObjectResponse;
import cc.ccoder.ufs.oss.qiniu.client.QiniuOssClient;
import cc.ccoder.ufs.oss.qiniu.factory.QiniuOssClientFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date QiniuOssServiceImpl.java v1.0 2021/11/2 15:57
 */
@Slf4j
@Service
@ConditionalOnBean(QiniuOssClientFactory.class)
public class QiniuOssServiceImpl implements OssService {

    private final QiniuOssClientFactory qiniuOssClientFactory;

    /**
     * user object Meta data prefix
     */
    private static final String META_PREFIX = "x-qn-meta-";

    /**
     * get bucket domain host
     */
    private static final String BUCKET_DOMAIN_API_HOST = "https://uc.qbox.me/v2/domains?tbl=%s";

    public QiniuOssServiceImpl(QiniuOssClientFactory qiniuOssClientFactory) {
        this.qiniuOssClientFactory = qiniuOssClientFactory;
    }

    @Override
    public PutObjectResponse putObject(PutObjectRequest request) throws Exception {
        QiniuOssClient qiniuOssClient = qiniuOssClientFactory.create();
        UploadManager uploadManager = qiniuOssClient.getUploadManager();
        try (InputStream inputStream = new FileInputStream(request.getFile())) {
            String uploadToken = qiniuOssClient.getAuth().uploadToken(request.getBucket());
            StringMap metadata = null;
            if (!request.getMetaData().isEmpty()) {
                metadata = buildUserMeta(request.getMetaData());
            }
            log.info("qiniu oss putObject bucket:{},ossPath:{}", request.getBucket(), request.getOssPath());
            Response response = uploadManager.put(inputStream, request.getOssPath(), uploadToken, metadata, null);
            log.info("qiniu oss putObject response status:{},requestId:{}", response.statusCode, response.reqId);
        }
        return new PutObjectResponse();
    }

    @Override
    public GetObjectUrlResponse getObjectUrl(GetObjectUrlRequest request, String bucketAccess) throws Exception {
        QiniuOssClient qiniuOssClient = qiniuOssClientFactory.create();
        Auth auth = qiniuOssClient.getAuth();
        try {
            String url = String.format(BUCKET_DOMAIN_API_HOST, request.getBucket());
            String authorization = auth.signQiniuAuthorization(url, "GET", null, (Headers)null);
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request okRequest = new Request.Builder().url(url).method("GET", null)
                .addHeader("Authorization", "Qiniu " + authorization).build();
            okhttp3.Response response = client.newCall(okRequest).execute();
            List<String> domainList = buildResponse(response);

            DownloadUrl downloadUrl = new DownloadUrl(domainList.get(0), false, request.getOssPath());
            log.info("qiniu oss getObjectUrl bucket:{},bucketDomain:{},ossPath:{}", request.getBucket(),
                domainList.get(0), request.getOssPath());
            String result = downloadUrl.buildURL(auth, request.getExpireSeconds());
            return GetObjectUrlResponse.builder().url(result).build();
        } finally {
            log.info("qiniu oss getObjectUrl complete");
        }

    }

    @Override
    public void getObject(GetObjectRequest request) throws Exception {

    }

    @Override
    public String getServiceCode() {
        return OssProviderEnum.QINIU.getCode();
    }

    private StringMap buildUserMeta(Map<String, String> metaData) {
        StringMap stringMap = new StringMap();
        metaData.forEach((key, value) -> {
            String metaKey = META_PREFIX + CaseFormatUtil.fromLowerCamelToLowerHyphen(key);
            stringMap.put(metaKey, value);
        });
        return stringMap;
    }

    private List<String> buildResponse(okhttp3.Response response) throws IOException {
        String domainResponse = new String(response.body().bytes());
        if (!response.isSuccessful()) {
            throw new ErrorException(domainResponse);
        }
        List domainList = JSONObject.parseObject(domainResponse, List.class);
        if (domainList.isEmpty()) {
            throw new ErrorException("qiniu bucket domain empty");
        }
        return domainList;
    }
}
