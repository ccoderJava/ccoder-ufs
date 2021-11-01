package cc.ccoder.ufs.oss.api.enums;

import org.apache.commons.lang3.StringUtils;

import cc.ccoder.common.base.CodeEnum;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date OssProviderEnm.java v1.0 2021/11/1 17:25
 */
public enum OssProviderEnm implements CodeEnum {
    /**
     * Aliyun OSS Service
     */
    ALIYUN("aliyun");

    private final String code;

    public String getCode() {
        return code;
    }

    OssProviderEnm(String code) {
        this.code = code;
    }

    public static OssProviderEnm getCode(String code) {
        for (OssProviderEnm type : OssProviderEnm.values()) {
            if (StringUtils.equalsIgnoreCase(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }
}
