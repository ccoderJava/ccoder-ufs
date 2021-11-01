package cc.ccoder.ufs.oss.api;

import cc.ccoder.common.template.factory.CodeService;
import cc.ccoder.ufs.oss.api.request.GetObjectUrlRequest;
import cc.ccoder.ufs.oss.api.request.PutObjectRequest;
import cc.ccoder.ufs.oss.api.response.GetObjectUrlResponse;
import cc.ccoder.ufs.oss.api.response.PutObjectResponse;

/**
 * <p>
 * OSS Storage Service interface
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date OssService.java v1.0 2021/11/1 15:53
 */
public interface OssService extends CodeService {

    /**
     * simple upload
     *
     * @param request
     *            request object,including bucket,ossPath,uploaded file etc
     * @return upload response
     * @throws Exception
     *             exception during execution upload
     */
    PutObjectResponse putObject(PutObjectRequest request) throws Exception;

    /**
     * get object url
     * 
     * @param request
     *            request object
     * @param bucketAccess
     *            bucket access
     * @return return target file access url
     * @throws Exception
     *             exception during execution get object url
     */
    GetObjectUrlResponse getObjectUrl(GetObjectUrlRequest request, String bucketAccess) throws Exception;

}
