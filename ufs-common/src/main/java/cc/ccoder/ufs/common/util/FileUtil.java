package cc.ccoder.ufs.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import cc.ccoder.common.exception.ErrorException;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date FileUtil.java v1.0 2021/11/1 18:07
 */
public class FileUtil {

    public static File createFile(String fileName, String context) {
        Charset utf8 = StandardCharsets.UTF_8;
        try {
            if (StringUtils.isBlank(context)) {
                context = StringUtils.EMPTY;
            }
            Path path = Files.write(Paths.get(fileName), context.getBytes());
            return path.toFile();
        } catch (IOException e) {
            throw new ErrorException("generate test file exception", e);
        }
    }

    public static void removeFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
