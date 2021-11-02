package cc.ccoder.ufs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.ccoder.ufs.common.util.FileUtil;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.enums.BucketAccessEnum;
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
public class TestController {

    @Autowired
    private OssService ossService;

    @RequestMapping(value = "/test")
    public void test() throws Exception {
        PutObjectResponse putObjectResponse = ossService.putObject(buildPutObjectRequest());
    }

    @RequestMapping(value = "/get/{ossPath}")
    public String testGet(@PathVariable("ossPath") String ossPath) throws Exception {
        GetObjectUrlRequest request = new GetObjectUrlRequest();
        request.setBucket("ufs-test");
        request.setExpireSeconds(10 * 60);
        request.setOssPath(ossPath);
        GetObjectUrlResponse objectUrl = ossService.getObjectUrl(request, BucketAccessEnum.PUBLIC_READ_WRITE.getCode());
        return "redirect:" + objectUrl.getUrl();
    }

    @RequestMapping(value = "/callback/{ossPath}")
    public void testCallback(@PathVariable("ossPath") String ossPath) throws Exception {
        GetObjectRequest request =
            GetObjectRequest.builder().bucket("ufs-test").ossPath(ossPath).callback(new GetObjectRequest.Callback() {
                @Override
                public void process(GetObjectRequest.CallbackContext context) throws Exception {
                    File file = new File("down.txt");
                    if(file.exists()){
                        file.delete();
                    }
                    Files.copy(context.getInputStream(), Paths.get("down.txt"));
                }
            }).build();
        ossService.getObject(request);
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
