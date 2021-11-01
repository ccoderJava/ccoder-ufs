package cc.ccoder.ufs.test.oss;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cc.ccoder.ufs.common.util.FileUtil;
import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.request.PutObjectRequest;
import cc.ccoder.ufs.oss.api.response.PutObjectResponse;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date OssServiceTest.java v1.0 2021/11/1 18:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssServiceTest {

    private static final String SHARE_BUCKET = "";

    @Autowired
    private OssService ossService;

    @Test
    public void testPutObject() throws Exception {
        PutObjectRequest request = buildPutObjectRequest();
        PutObjectResponse putObjectResponse = ossService.putObject(request);
    }

    private PutObjectRequest buildPutObjectRequest() {

        File uploadFile = FileUtil.createFile("test.txt", "hello world");

        PutObjectRequest request = new PutObjectRequest();
        request.setBucket(SHARE_BUCKET);
        request.setOssPath(uploadFile.getName());
        request.setFile(uploadFile);
        request.setEncrypt(false);
        return request;
    }

}
