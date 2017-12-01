package com.tgnet.app.utils.utils;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.czt.mp3recorder.util.LameUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.media.AudioFormat.CHANNEL_IN_MONO;
import static android.media.AudioRecord.STATE_INITIALIZED;

/**
 * Created by fan-gk on 2017/4/27.
 */

public class AudioRecorder {

    private final static int LAME_CHANNEL_IN_MONO = 1;
    private final static int LAME_MP3_BIT = 16;
    private static final int LAME_MP3_QUALITY = 7;

    public interface OnVolumChangedListener{
        /**
         *
         * @param level 1-8
         */
        void onVolumChanged(double level);
    }

    private final Context context;
    private final int sampleRateInHz;
    private final int audioFormat;
    private OnVolumChangedListener volumChangedListener;
    private AudioRecord audioRecord;
    private Thread audioRecordThread;
    private short[] buffer;
    private byte[] mp3Buffer;

    public ByteArrayOutputStream getStream() {
        return stream;
    }

    private ByteArrayOutputStream stream;
    private double volumValue;
    private boolean recording;

    public AudioRecorder(Context context){
        this.context = context;
        this.sampleRateInHz = 8000;
        this.audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    }

    public synchronized void start(){
        Log.d("AudioRecorder", "start begin");
        if(audioRecord == null) {
            int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, CHANNEL_IN_MONO, audioFormat);
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz, CHANNEL_IN_MONO, audioFormat, bufferSize);
            buffer = new short[bufferSize];
            mp3Buffer = new byte[(int) (7200 + 1.25 * 2 * bufferSize)];
            stream = new ByteArrayOutputStream(bufferSize);
        }
        if(recording)
            return;

        reset();

        audioRecord.startRecording();
        stream.reset();
        recording = true;
        audioRecordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                readAudioRecord();
            }
        });
        audioRecordThread.start();
        Log.d("AudioRecorder", "start end");
    }

    private void reset(){
        if(audioRecordThread != null){
            try {
                audioRecordThread.interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
            audioRecordThread = null;
        }
        recording = false;
    }

    private void readAudioRecord(){
        LameUtil.init(sampleRateInHz, LAME_CHANNEL_IN_MONO, sampleRateInHz, LAME_MP3_BIT, LAME_MP3_QUALITY);
        while (recording) {
            int len;
            while ((len = audioRecord.read(buffer, 0, buffer.length)) > 0)
            {
                saveMp3ToStreamFromPcm(buffer, len);

                double sumVolumValue = 0;
                for (int i = 0; i < len; i++) {
                    sumVolumValue += buffer[i] * buffer[i];
                }
                // 平方和除以数据总长度，得到音量大小。
                double mean = sumVolumValue / (double) len;
                double volume = Math.min(Math.max(1, (10 * Math.log10(mean) - 40) / 5), 8);

                if (volume != volumValue) {
                    volumValue = volume;
                    if (volumChangedListener != null)
                        volumChangedListener.onVolumChanged(volume);
                }
            }
        }
        LameUtil.close();
    }

    public synchronized void stop(){
        Log.d("AudioRecorder", "stop begin");
        if(audioRecord != null && recording) {
            try {
                audioRecord.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        reset();
        Log.d("AudioRecorder", "stop end");
    }

    public void write(OutputStream stream) throws IOException {
        if(this.stream != null && this.stream.size() > 0){
            this.stream.writeTo(stream);
        }
    }

    public void setOnVolumChangedListener(OnVolumChangedListener listener){
        this.volumChangedListener = listener;
    }

    /**
     * 采样频率
     * @return
     */
    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public boolean isInitialized(){
        return audioRecord.getState() == STATE_INITIALIZED;
    }

    private void saveMp3ToStreamFromPcm(short[] buffer, int len){
        int mp3Len = LameUtil.encode(buffer, buffer, len, mp3Buffer);
        this.stream.write(mp3Buffer, 0, mp3Len);
    }

    public synchronized void release(){
        if(audioRecord != null) {
            try {
                audioRecord.release();
                audioRecord = null;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
