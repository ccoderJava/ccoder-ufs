package cc.ccoder.ufs.oss.api.request;

import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date PutObjectRequest.java v1.0 2021/11/1 15:56
 */
@Data
public class PutObjectRequest implements Serializable {

    private String bucket;
    private String ossPath;
    private File file;
    private Boolean forbidOverwrite;
    private Boolean encrypt;
    private Map<String, String> metaData;
}
