package cc.ccoder.ufs.oss.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date GetObjectUrlRequest.java v1.0 2021/11/1 17:33
 */
@Data
public class GetObjectUrlRequest implements Serializable {

    /**
     * bucket name
     */
    private String bucket;

    /**
     * the oss path of target file
     */
    private String ossPath;

    /**
     * expire seconds
     */
    private long expireSeconds;

}
