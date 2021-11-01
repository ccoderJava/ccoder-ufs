package cc.ccoder.ufs.common.util;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date CaseFormatUtil.java v1.0 2021/11/1 17:48
 */
public class CaseFormatUtil {

    /**
     * LOWER_CAMEL 格式转换为 LOWER_HYPHEN 格式
     *
     * @param original
     *            原文
     * @return 结果
     */
    public static String fromLowerCamelToLowerHyphen(String original) {
        if (original == null) {
            return null;
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, original);
    }

    /**
     * LOWER_CAMEL 格式转换为 LOWER_HYPHEN 格式
     *
     * @param original
     *            原文
     * @return 结果
     */
    public static String fromLowerHyphenToLowerCamel(String original) {
        if (original == null) {
            return null;
        }
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, original);
    }

    /**
     * 判断原文是否能从 LOWER_CAMEL 转换为 LOWER_HYPHEN，再转换回LOWER_CAMEL，结果是否一样
     *
     * @param original
     *            原文
     * @return 是否转换无损失
     */
    public static boolean isTransformableFromLowerCamel(String original) {
        return StringUtils.equals(original, fromLowerHyphenToLowerCamel(fromLowerCamelToLowerHyphen(original)));
    }

}
