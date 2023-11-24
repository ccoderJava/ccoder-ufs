package cc.ccoder.ufs.zip;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.InputStream;
import java.io.Serializable;

public class ZipResponse implements Serializable {
    
    private String fileKey;
    
    private InputStream inputStream;
    
    private boolean exist = false;
    
    public ZipResponse setExist(boolean exist){
        this.exist = exist;
        return this;
    }
    
    public ZipResponse setFileKey(String fileKey){
        this.fileKey = fileKey;
        return this;
    }
    
    public ZipResponse setInputStream(InputStream inputStream){
        this.inputStream = inputStream;
        return this;
    }

    public String getFileKey() {
        return fileKey;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public boolean isExist() {
        return exist;
    }

    public ZipResponse() {
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
