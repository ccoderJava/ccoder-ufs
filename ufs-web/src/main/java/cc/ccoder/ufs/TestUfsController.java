package cc.ccoder.ufs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.qiniu.http.Headers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiniu.util.Auth;

import cc.ccoder.ufs.common.util.FileUtil;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.enums.BucketAccessEnum;
import cc.ccoder.ufs.oss.api.integration.OssServiceFactory;
import cc.ccoder.ufs.oss.api.request.GetObjectRequest;
import cc.ccoder.ufs.oss.api.request.GetObjectUrlRequest;
import cc.ccoder.ufs.oss.api.request.PutObjectRequest;
import cc.ccoder.ufs.oss.api.response.GetObjectUrlResponse;
import cc.ccoder.ufs.oss.api.response.PutObjectResponse;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date Controller.java v1.0 2021/11/1 18:38
 */
@Controller
@RequestMapping(value = "/ufs")
public class TestUfsController {

    private final OssServiceFactory ossServiceFactory;

    private OssService ossService;

    public TestUfsController(OssServiceFactory ossServiceFactory) {
        this.ossServiceFactory = ossServiceFactory;
    }

    @PostConstruct
    public void setOssService() {
        ossService = ossServiceFactory.getService();
    }

    @RequestMapping(value = "/{bucketName}/put/")
    public String put(@PathVariable(value = "bucketName") String bucketName) throws Exception {
        PutObjectRequest request = buildPutObjectRequest(bucketName);
        PutObjectResponse putObjectResponse = ossService.putObject(request);
        return "upload success";

    }

    @RequestMapping(value = "/{bucketName}/get/{ossPath}")
    public String testGet(@PathVariable(value = "bucketName") String bucketName,
        @PathVariable(value = "ossPath") String ossPath) throws Exception {
        GetObjectUrlRequest request = new GetObjectUrlRequest();
        request.setBucket(bucketName);
        request.setExpireSeconds(10 * 60);
        request.setOssPath(ossPath);
        GetObjectUrlResponse objectUrl = ossService.getObjectUrl(request, BucketAccessEnum.PUBLIC_READ_WRITE.getCode());
        return "redirect:" + objectUrl.getUrl();
    }

    @RequestMapping(value = "/{bucketName}/down/{ossPath}")
    public String testCallback(@PathVariable(value = "bucketName") String bucketName,
        @PathVariable(value = "ossPath") String ossPath) throws Exception {
        GetObjectRequest request =
            GetObjectRequest.builder().bucket(bucketName).ossPath(ossPath).callback(new GetObjectRequest.Callback() {
                @Override
                public void process(GetObjectRequest.CallbackContext context) throws Exception {
                    File file = new File("down.txt");
                    if (file.exists()) {
                        file.delete();
                    }
                    Files.copy(context.getInputStream(), Paths.get("down.txt"));
                }
            }).build();
        ossService.getObject(request);
        return "download success";
    }

    private PutObjectRequest buildPutObjectRequest(String bucketName) {
        File uploadFile = FileUtil.createFile("test.txt", "hello world");
        PutObjectRequest request = new PutObjectRequest();
        request.setBucket(bucketName);
        request.setOssPath(uploadFile.getName());
        request.setFile(uploadFile);
        request.setEncrypt(false);
        request.setForbidOverwrite(false);
        Map<String, String> metaData = new HashMap<>();
        metaData.put("userName", "chencong");
        metaData.put("fileName", uploadFile.getName());
        metaData.put("time", System.currentTimeMillis() + "");
        request.setMetaData(metaData);
        return request;
    }

}
