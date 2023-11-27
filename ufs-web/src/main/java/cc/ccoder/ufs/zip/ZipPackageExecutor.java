package cc.ccoder.ufs.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class ZipPackageExecutor implements AutoCloseable{

    private final ThreadPoolExecutor executor;
    private Logger log = LoggerFactory.getLogger(ZipPackageExecutor.class);

    private List<ZipResponse> responses = Collections.synchronizedList(new ArrayList<>());

    public ZipPackageExecutor(){
        this(ZipThreadPoolManager.getPool());
    }

    public ZipPackageExecutor(ThreadPoolExecutor executor){
        this.executor = executor;
    }

    public ZipPackageExecutor execute(List<ZipRequest> list){
        if(CollectionUtils.isEmpty(list)){
            return this;
        }
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (ZipRequest zipRequest : list) {
            this.executor.execute(new ZipTask(this.responses,zipRequest,countDownLatch));
        }
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            log.error("zip download fail:",e);
            throw new RuntimeException(e);
        }
        return this;
    }

    public List<ZipResponse> list() {
        return this.responses;
    }

    @Override
    public void close() throws Exception {

    }
}
