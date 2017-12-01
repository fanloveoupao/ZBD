package com.tgnet.io;

import com.tgnet.util.StringUtil;
import com.tgnet.util.VerifyUtils;

import java.io.File;

/**
 * Created by fan-gk on 2017/4/24.
 */


public class FileUtil {
    public static String getExtension(String filename) {
        if (filename == null)
            return StringUtil.EMPTY;

        int index = filename.lastIndexOf(".");
        if (index == -1)
            return StringUtil.EMPTY;

        return filename.substring(index);
    }

    public static String getMimeType(String extension) {
        if (extension == null)
            return "image/jpeg";
        switch (extension.toLowerCase()) {
            case ".png":
                return "image/png";
            case ".jpg":
            case ".jpeg":
            default:
                return "image/jpeg";
        }
    }

    public static String getFileNameWithoutExtension(File file) {
        String name = file.getName().substring(0, file.getName().lastIndexOf("."));
        return VerifyUtils.noSign(name);
    }
}

