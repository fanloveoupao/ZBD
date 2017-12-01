package com.zbd.app;

import com.tgnet.app.utils.utils.FileHelper;
import com.tgnet.app.utils.utils.ImageHelper;
import com.tgnet.log.LoggerResolver;
import com.ysnet.zdb.file.ILocalMessageContentHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author fan-gk
 * @date 2017/11/30.
 */
public class LocalMessageContentHandler implements ILocalMessageContentHandler {
    @Override
    public byte[] compressImage(String file, int maxWidth, int maxHeight, int maxSize) {
        ByteArrayOutputStream stream = null;
        try{
            stream = ImageHelper.compressImage(new File(file), maxWidth, maxHeight, maxSize);
            return stream.toByteArray();
        }
        catch (Exception e){
            LoggerResolver.getInstance().fail("上传图片失败", e);
            return null;
        }
        finally {
            if(stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public byte[] getFileBytes(String file) {
        try {
            File f = new File(file);
            FileInputStream fileInputStream = new FileInputStream(f);
            byte[] buffer = new byte[(int) f.length()];
            fileInputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String saveRecordFile(String filename, ByteArrayOutputStream stream) {
        return FileHelper.saveRecordFile(filename, stream.toByteArray());
    }
}
