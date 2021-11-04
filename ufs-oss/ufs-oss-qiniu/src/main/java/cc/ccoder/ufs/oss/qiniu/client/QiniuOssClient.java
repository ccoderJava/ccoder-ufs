package cc.ccoder.ufs.oss.qiniu.client;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date QiniuClient.java v1.0  2021/11/2 16:03
 */
@Data
@Builder
public class QiniuOssClient implements Serializable {

    private UploadManager uploadManager;

    private Auth auth;
}
