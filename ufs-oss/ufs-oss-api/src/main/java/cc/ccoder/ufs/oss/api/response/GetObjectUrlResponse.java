package cc.ccoder.ufs.oss.api.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date GetObjectUrlResponse.java v1.0 2021/11/1 17:32
 */
@Data
@Builder
public class GetObjectUrlResponse implements Serializable {

   private String url;
}
