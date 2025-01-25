package com.congyan.controller;

import com.congyan.entity.vo.request.DysarthriaBase64VO;
import com.congyan.entity.vo.request.SinglePronounceVO;
import com.congyan.entity.vo.request.TtsTextVO;
import com.congyan.entity.vo.response.*;
import com.congyan.entity.RestBean;
import com.congyan.service.DysarthriaTestService;
import com.congyan.utils.FileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/dysarthria")
@Tag(name = "获取构音数据",description = "根据录音，获取单个或者多个的发音分析")
public class DysarthriaController {

    @Resource
    private DysarthriaTestService dysarthriaTestService;

    /**
     * 获取文本发音分析
     * @param file 音频文件，推荐m4a格式
     * @param text 原文本
     * @return 返回每个汉字的详细发音信息
     */
    @PostMapping("/getResult")
    @Operation(summary = "请求构音数据分析")
    public RestBean<DysarthriaResultVO> getTestResult(@RequestParam("audioFile") MultipartFile file, @RequestParam("text") String text){
        File tempFile;
        // 指定临时文件保存目录
        File tempDir = new File("./temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs(); // 如果目录不存在，则创建目录
        }
        if (file.isEmpty()) {
            return RestBean.failure(2004,"empty file");
        }


        //保存音频文件
        try{
            // 保存文件到临时路径
            tempFile = File.createTempFile("inputAudio", ".m4a",tempDir);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(file.getBytes());
        }
        catch (IOException e) {
            return RestBean.failure(1003,"文件写入失败，请联系管理员");
        }


        DysarthriaResultVO result = null;
        try {
            result = dysarthriaTestService.
                    getDysarthriaResult(text.replaceAll("[^\\u4e00-\\u9fa5]", ""),
                            tempFile);
        } catch (Exception e) {
            return RestBean.failure(3002,"音频识别出错，请联系管理员");
        }
        tempFile.delete();

        return RestBean.success(result);
    }


    /**
     * 根据base64编码获取发音数据
     * @param dysarthriaBase64VO 文本和发音base64
     * @return 返回每个汉字的详细发音信息
     */
    @PostMapping("/getResultByBase64")
    @Operation(description = "根据base64编码获取构音数据分析")
    public RestBean<DysarthriaResultVO> getResultByBase64(@RequestBody @Valid
                                                              DysarthriaBase64VO dysarthriaBase64VO){
        File tempFile;
        //去除前缀
        String base64String = dysarthriaBase64VO.getAudioBase64();
        String base64 = base64String.substring(base64String.indexOf(",") + 1);

        if(base64.isEmpty()){
            return RestBean.failure(2004,"空base64文件");
        }

        // 解码 Base64 字符串
        byte[] fileBytes = Base64.getDecoder().decode(base64);


        // 指定临时文件保存目录
        File tempDir = new File("./temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs(); // 如果目录不存在，则创建目录
        }
        try {
            tempFile = File.createTempFile("inputAudio", ".m4a",tempDir);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(fileBytes);
        } catch (IOException e) {
            return RestBean.failure(1001,"base64文件写入失败，请联系管理员");
        }
        DysarthriaResultVO result = null;
        try {
            result = dysarthriaTestService.
                    getDysarthriaResult(dysarthriaBase64VO.
                            getText().replaceAll("[^\\u4e00-\\u9fa5]", ""),
                            tempFile);
        } catch (Exception e) {
            return RestBean.failure(3002,"音频识别出错，请联系管理员");
        }
        tempFile.delete();
        //System.out.println("delete");
        return RestBean.success(result);

    }


    /**
     * 获取文本拼音声母韵母，发音方法
     * @param text 原文本
     * @return 每个汉字的拼音声母韵母列表
     */
    @GetMapping("/getPinyinDetail")
    @Operation(summary = "获取汉字详细发音")
    public RestBean<PinyinDetailVO> getPinyinDetail(@RequestParam("text") String text){

        try {
            return RestBean.success(
                    dysarthriaTestService.
                    getPinyinDetailVO(text.replaceAll("[^\\u4e00-\\u9fa5]", ""))
            );
        } catch (Exception e) {
            return RestBean.failure(2005,"拼音解析失败，请联系管理员");
        }
    }



    /**
     * 获取文本拼音
     * @param text 原文本
     * @return 每个汉字的拼音，不包括声母韵母列表
     */
    @GetMapping("/getPinyin")
    public RestBean<PinyinVO> getPinyin(@RequestParam("text") String text){
        return  RestBean.success(dysarthriaTestService.getPinyin(text.replaceAll("[^\\u4e00-\\u9fa5]", "")));
    }

    @PostMapping("/getSinglePronounceByBase64")
    public  RestBean<SinglePronounceDetailVO> getSinglePronounceByBase64(@RequestBody @Valid SinglePronounceVO pronounceVO){
        File tempFile;
        //去除前缀
        String base64String = pronounceVO.getAudioBase64();
        String base64 = base64String.substring(base64String.indexOf(",") + 1);

        if(base64.isEmpty()){
            return RestBean.failure(2004,"空base64文件");
        }

        // 解码 Base64 字符串
        byte[] fileBytes = Base64.getDecoder().decode(base64);


        // 指定临时文件保存目录
        File tempDir = new File("./temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs(); // 如果目录不存在，则创建目录
        }
        try {
            tempFile = File.createTempFile("inputAudio", ".m4a",tempDir);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(fileBytes);
        } catch (IOException e) {
            return RestBean.failure(1001,"base64文件写入失败，请联系管理员");
        }
        try {
            return RestBean.success(dysarthriaTestService.getSinglePronounceDetail(pronounceVO.getCharacter(),tempFile));
        } catch (Exception e) {
            return RestBean.failure(1002,"数据库问题，请联系管理员");
        }
    }





    /**
     * 获取tts音频文件base64编码
     * @param text 原文本
     * @return 音频文件base64编码，byte[]，mp3格式
     */
    @PostMapping("/getTTS")
    public RestBean<TtsVO> getTTS(@RequestBody @Valid TtsTextVO text){

        try {
            TtsVO ttsVO = dysarthriaTestService.getTTS(text.getText());
            return RestBean.success(ttsVO);
        } catch (Exception e) {
            return RestBean.failure(3001,"tts工具网络错误，请联系管理员");

        }
    }





}
