package com.ysnet.zdb.file;

import java.io.ByteArrayOutputStream;

/**
 * @author fan-gk
 * @date 2017/11/30.
 */
public interface ILocalMessageContentHandler {
    /**
     * 压缩图片
     * @param file
     * @param maxWidth
     * @param maxHeight
     * @param maxSize
     * @return 成功返回压缩后的图片字节数组，失败返回null
     */
    byte[] compressImage(String file, int maxWidth, int maxHeight, int maxSize);

    byte[] getFileBytes(String file);

    String saveRecordFile(String filename, ByteArrayOutputStream stream);
}