package com.congyan.service.impl;

import com.congyan.entity.dto.*;
import com.congyan.entity.vo.response.*;
import com.congyan.service.DysarthriaTestService;
import com.congyan.service.InitialDataService;
import com.congyan.service.VowelDataService;
import com.congyan.utils.FileUtils;
import com.congyan.utils.PinyinUtil;
import com.congyan.utils.WebIATWS;
import com.congyan.utils.WebTtsWs;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
@Slf4j
public class DysarthriaTestServiceImpl implements DysarthriaTestService {
    @Resource
    InitialDataService initialService;
    @Resource
    VowelDataService vowelService ;



    /**
     * 转换文件格式，获取发音文本，获取具体的发音参数
     * @param text 原文本
     * @param pronounceFile 发音音频文件
     * @return 返回一个DysarthriaTestResult类，包含每个字的具体发音方式正误和得分
     * @throws Exception 语言识别API可能有网络错误
     */
    @Override
    public DysarthriaResultVO getDysarthriaResult(String text, File pronounceFile) throws Exception {

        // 指定临时文件保存目录
        File tempDir = new File("./temp");
        File  tempPCMFile = File.createTempFile("Audio", ".pcm",tempDir);
        //转换文件格式
        FileUtils.ConvertM4aToPcm(pronounceFile,tempPCMFile);
        //使用语言识别获取文本
        String pronounceText = new WebIATWS().getPronounceText(tempPCMFile);

        return PinyinUtil.analysis(text,pronounceText);
    }


    /**
     * 获取文本拼音声母韵母列表
     * 查询声母韵母详细发音
     * @param text 原文本
     * @return 返回detail拼音VO
     * @throws Exception 未找到韵母
     */
    @Override
    public PinyinDetailVO getPinyinDetail(String text) {
        TextPinyin textPinyin = null;
        try {
            textPinyin = PinyinUtil.getTextPinyin(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Initial> initialDetailList = initialService.getInitialDetailList(textPinyin.getSm());
        System.out.println(initialDetailList);
        List<Vowel> vowelDetailList = null;
        try {
            vowelDetailList = vowelService.getVowelDetailList(textPinyin.getYm());
            ;
            System.out.println(vowelDetailList);
        } catch (Exception e) {
            System.out.println("ym mysql");
            e.printStackTrace();
        }

        try {

            new PinyinDetail(textPinyin, initialDetailList, vowelDetailList).asViewObject(PinyinDetailVO.class);
        } catch (Exception e) {
            System.out.println("2");
        }
        return new PinyinDetail(textPinyin,initialService.getInitialDetailList(textPinyin.getSm()),vowelService.getVowelDetailList(textPinyin.getYm())).asViewObject(PinyinDetailVO.class);
    }




    /**
     * 获取文本拼音，不区分声母韵母
     * @param text 文本
     * @return 返回拼音VO
     */
    @Override
    public PinyinVO getPinyin(String text) {
        return PinyinUtil.getPinYinList(text);
    }

    /**
     * 获取合成语音
     * @param text 原文本
     * @return 返回VO
     * @throws Exception 第三方API请求失败
     */
    @Override
    public TtsVO getTTS(String text) throws Exception {
        return new TtsVO(new WebTtsWs().getTts(text));
    }

    /**
     * 先转换文件格式，然后获取汉字发音，包装汉字拼音
     * @param character 单个汉字
     * @param temFile 临时音频文件，可以不是pcm格式
     * @return
     */
    @Override
    public SinglePronounceDetailVO getSinglePronounceDetail(char character,File temFile) throws Exception {
        // 指定临时文件保存目录
        File tempDir = new File("./temp");
        File  tempPCMFile = File.createTempFile("Audio", ".pcm",tempDir);
        //转换文件格式
        FileUtils.ConvertM4aToPcm(temFile,tempPCMFile);
        //使用语言识别获取文本
        String pronounceText = new WebIATWS().getPronounceText(tempPCMFile);
        TextPinyin characterPinyin = PinyinUtil.getTextPinyin(String.valueOf(pronounceText.charAt(0)));
        Pronounce pronounce = null;
        try {
            pronounce = new Pronounce(characterPinyin.getSd().get(0),
                    initialService.getOneInitialDetail(characterPinyin.getSm().get(0)),
                    vowelService.getOneVowelDetail(characterPinyin.getYm().get(0)));
        } catch (Exception e) {
            log.error("数据库操作失败");
            throw new RuntimeException(e);
        }
        return pronounce.asViewObject(SinglePronounceDetailVO.class);
    }


}
