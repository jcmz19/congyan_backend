package com.congyan.utils;

import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class FileUtils {

    /**
     * 将m4a转换成pcm格式
     * @param input m4a格式文件
     * @param output 输出文件，必须为空
     */
    public static void ConvertM4aToPcm(File input,File output){

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le"); // 音频编解码器为16位线性PCM
        audio.setBitRate(256000); // 比特率
        audio.setChannels(1); // 设置为单声道
        audio.setSamplingRate(16000); // 设置采样率为16kHz

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("wav"); // PCM通常封装在WAV容器中
        attrs.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        try {
            encoder.encode(new MultimediaObject(input), output, attrs);
            System.out.println("转换成功！");
        } catch (EncoderException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断是否为m4a格式
     * @param file 文件
     * @return 是或否
     */
    public static boolean isM4AFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // 检查文件扩展名
        String fileExtension = getFileExtension(file);
        if (!"m4a".equalsIgnoreCase(fileExtension)) {
            return false;
        }

        // 检查MIME类型
        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }

        // 判断MIME类型是否为M4A
        return "audio/x-m4a".equalsIgnoreCase(contentType) ||
                "audio/mp4".equalsIgnoreCase(contentType) ||
                "audio/aac".equalsIgnoreCase(contentType);
    }

    /**
     * 获取文件扩展名
     * @param file 文件
     * @return 扩展名
     */
    public static String getFileExtension(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return null; // 没有扩展名或扩展名为空
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
