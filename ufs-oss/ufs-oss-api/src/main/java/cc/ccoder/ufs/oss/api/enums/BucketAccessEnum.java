package cc.ccoder.ufs.oss.api.enums;

import org.apache.commons.lang3.StringUtils;

import cc.ccoder.common.base.CodeEnum;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date BucketAccessEnum.java v1.0 2021/11/1 18:51
 */
public enum BucketAccessEnum implements CodeEnum {

    /**
     * 私有
     */
    PRIVATE("private"),

    /**
     * 公共读
     */
    PUBLIC_READ("public-read"),

    /**
     * 公共读写
     */
    PUBLIC_READ_WRITE("public-read-write"),;

    private final String code;

    BucketAccessEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static BucketAccessEnum getCode(String code) {
        for (BucketAccessEnum type : BucketAccessEnum.values()) {
            if (StringUtils.equalsIgnoreCase(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }
}
