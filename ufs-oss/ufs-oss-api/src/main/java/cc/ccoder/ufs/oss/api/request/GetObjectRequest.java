package cc.ccoder.ufs.oss.api.request;

import java.io.InputStream;
import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date GetObjectRequest.java v1.0 2021/11/1 19:24
 */
@Data
@Builder
public class GetObjectRequest implements Serializable {

    @NotBlank
    private String bucket;

    @NotBlank
    private String ossPath;

    @NotNull
    @ToString.Exclude
    Callback callback;

    public interface Callback {

        /**
         * process response
         * 
         * @param context
         *            callback context
         * 
         */
        void process(CallbackContext context) throws Exception;

    }

    /**
     * callback context
     */
    @Data
    public static class CallbackContext {
        GetObjectRequest request;
        InputStream inputStream;
        String contentType;
        String contentDisposition;
    }

}
