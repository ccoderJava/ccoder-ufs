package cc.ccoder.ufs;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.ccoder.ufs.common.util.FileUtil;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.enums.BucketAccessEnum;
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
public class TestController {

    @Autowired
    private OssService ossService;

    @RequestMapping(value = "/test")
    public void test() throws Exception {
        PutObjectResponse putObjectResponse = ossService.putObject(buildPutObjectRequest());
    }

    @RequestMapping(value = "/get/{ossPath}")
    public String testGet() throws Exception {
        GetObjectUrlRequest request = new GetObjectUrlRequest();
        request.setBucket("ufs-test");
        request.setExpireSeconds(10 * 60);
        request.setOssPath("test.txt");
        GetObjectUrlResponse objectUrl = ossService.getObjectUrl(request, BucketAccessEnum.PUBLIC_READ_WRITE.getCode());
        return "redirect:" + objectUrl.getUrl();
    }

    private PutObjectRequest buildPutObjectRequest() {

        File uploadFile = FileUtil.createFile("test.txt", "hello world");

        PutObjectRequest request = new PutObjectRequest();
        request.setBucket("ufs-test");
        request.setOssPath(uploadFile.getName());
        request.setFile(uploadFile);
        request.setEncrypt(false);
        request.setForbidOverwrite(false);
        return request;
    }

}
