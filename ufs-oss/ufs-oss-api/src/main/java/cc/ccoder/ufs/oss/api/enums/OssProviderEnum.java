package cc.ccoder.ufs.oss.api.enums;

import org.apache.commons.lang3.StringUtils;

import cc.ccoder.common.base.CodeEnum;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date OssProviderEnum.java v1.0 2021/11/1 17:25
 */
public enum OssProviderEnum implements CodeEnum {
    /**
     * Aliyun OSS Service
     */
    ALIYUN("aliyun"),

    /**
     * Qiniu OSS Service
     */
    QINIU("qiniu");

    private final String code;

    @Override
    public String getCode() {
        return code;
    }

    OssProviderEnum(String code) {
        this.code = code;
    }

    public static OssProviderEnum getCode(String code) {
        for (OssProviderEnum type : OssProviderEnum.values()) {
            if (StringUtils.equalsIgnoreCase(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }
}
