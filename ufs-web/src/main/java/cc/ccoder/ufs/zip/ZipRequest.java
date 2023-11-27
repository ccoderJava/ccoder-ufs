package cc.ccoder.ufs.zip;

public class ZipRequest {

    private String bucket;
    private String fileKey;

    public ZipRequest setBucket(String bucket){
        this.bucket = bucket;
        return this;
    }

    public ZipRequest setFileKey(String fileKey){
        this.fileKey = fileKey;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public String getFileKey() {
        return fileKey;
    }

    public ZipRequest() {
    }
}
