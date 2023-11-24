package cc.ccoder.ufs.zip;

import cc.ccoder.ufs.oss.api.OssService;
import cc.ccoder.ufs.oss.api.integration.OssServiceFactory;
import cc.ccoder.ufs.oss.api.request.GetObjectRequest;
import cc.ccoder.ufs.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZipTask implements Runnable{

    private static Logger log = LoggerFactory.getLogger(ZipTask.class);
    
    private final List<ZipResponse> collect;
    private final CountDownLatch countDownLatch;

    private OssService ossService;

    private final OssServiceFactory ossServiceFactory = SpringUtil.getBean(OssServiceFactory.class);

    private final String bucket;

    private final String fileKey;


    public ZipTask(List<ZipResponse> collect, ZipRequest zipRequest,CountDownLatch countDownLatch) {
        this.collect = collect;
        this.bucket = zipRequest.getBucket();
        this.fileKey = zipRequest.getFileKey();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
            log.info("star download file. bucket = [{}], fileKey = [{}]", this.bucket,this.fileKey);
        try {
            ZipResponse zipResponse = new ZipResponse();
            this.collect.add(zipResponse);
            ossService = ossServiceFactory.getService();
            zipResponse.setFileKey(this.fileKey);
            // TODO: 2023/11/24 处理回调
            ossService.getObject(null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            countDownLatch.countDown();
            log.info("complete download file.");
        }
    }
}
