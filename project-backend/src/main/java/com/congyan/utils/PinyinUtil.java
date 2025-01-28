package com.congyan.utils;



import com.congyan.entity.vo.response.DysarthriaResultVO;
import com.congyan.entity.dto.TextPinyin;
import com.congyan.entity.vo.response.PinyinVO;
import com.congyan.enums.pinyinEnum.SD_Enum;
import com.congyan.enums.pinyinEnum.SM_Enum;
import com.congyan.enums.pinyinEnum.YM_Enum;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.congyan.enums.pinyinEnum.*;
import org.springframework.beans.factory.annotation.Value;

//实现拼音比较的工具类
public abstract class PinyinUtil {

    private static final HashMap<String,Integer> SM = initSMMap();
    private static final HashMap<String,Integer> YM = initYMMap();


    /*
    满分100制
    声母：韵母：声调权重 = 4 ： 4 ：2
    声母得分细则：相同则为 40
                发音方式相同为SM_METHOD * 40
                发音部位相同为SM_PART * 40
                完全不同为0
    韵母得分细则：
                相同为30
                发音方式和口型都相同 YM_SAME_LIKE:YM_ALIKE * 40
                发音结构相同 YM_DIFF_SHAPE：YM_SHAPE * 40
                发音口型相同 YM_DIFF_STRUCT:YM_STRUCT * 40
                发音口型相同且粗结构相同 YM_DIFF_SMOOTH:YM_STRUCT_AND_ROUGH * 40
                发音粗结构相同 YM_DIFF_SHAPE_AND_SMOOTH:YM_ROUGH * 40
                完全不同为0
    声调得分细则：
                相同则为20
                不同则为0
     */
    @Value("${dysarthria.score-right.train.sm}")
    private static int SM_RIGHT;
    @Value("${dysarthria.score-right.train.ym}")
    private static int YM_RIGHT;
    @Value("${dysarthria.score-right.train.sd}")
    private static int SD_RIGHT;
    @Value("${dysarthria.score-right.train.total}")
    private static int TOTAL_RIGHT;
    @Value("${dysarthria.full-score}")
    private static int FULL_SCORE;
    private static final float SM_METHOD = SM_Enum.SM_DIFF_PART.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);
    private static final float SM_PART = SM_Enum.SM_DIFF_METHOD.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);

    private static final float YM_ALIKE = YM_Enum.YM_SAME_LIKE.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);
    private static final float YM_SHAPE = YM_Enum.YM_DIFF_STRUCT.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);
    private static final float YM_STRUCT = YM_Enum.YM_DIFF_SHAPE.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);
    private static final float YM_STRUCT_AND_ROUGH = YM_Enum.YM_DIFF_SMOOTH.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);
    private static final float YM_ROUGH = YM_Enum.YM_DIFF_SHAPE_AND_SMOOTH.getValue() * ((float) FULL_SCORE /TOTAL_RIGHT);




    private static HashMap<String,Integer> initSMMap(){
        HashMap<String,Integer> map = new HashMap<>();
        map.put("b",1);map.put("p",2);map.put("m",7);map.put("w",9);
        map.put("f",14);map.put("d",19);map.put("t",20);map.put("n",25);
        map.put("l",26);map.put("g",28);map.put("k",29);map.put("h",32);
        map.put("j",39);map.put("q",40);map.put("x",41);map.put("zh",48);
        map.put("ch",49);map.put("sh",50);map.put("r",51);map.put("z",57);map.put("c",58);
        map.put("s",59);map.put("y",63);

        return map;
    }

    private static HashMap<String,Integer> initYMMap(){
        HashMap<String,Integer> map = new HashMap<>();
        map.put("i",102);map.put("u",103);map.put("ü",104);map.put("a",105);
        map.put("o",109);map.put("e",113);map.put("ue",115);map.put("er",121);
        map.put("ai",201);map.put("ei",205);map.put("ie",206);map.put("üe",208);
        map.put("ao",209);map.put("ou",213);map.put("iu",214);map.put("ui",215);
        map.put("an",301);map.put("en",301);map.put("in",302);map.put("un",303);
        map.put("uan",303);map.put("ün",304);map.put("ang",305);map.put("eng",305);
        map.put("ong",305);map.put("ing",306);


        return map;
    }


    /**
     * 获取全部拼音，区分韵母声母
     * @param inputString 字段
     * @return 各个拼音，以","分割
     */
    public static String getPinYinDetail(String inputString) {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        char[] input = inputString.trim().toCharArray();
        StringBuilder output = new StringBuilder();

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output.append(temp[0]);
                    if (i < input.length-1) {
                        output.append(",");
                    }
                } else
                    output.append(Character.toString(input[i]));
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    /**
     * 使用第三方库获取某个文本的全部拼音,不区分声母韵母
     * @param inputString 文本
     * @return 各个拼音
     */
    public static PinyinVO getPinYinList(String inputString) {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        char[] input = inputString.trim().toCharArray();

        List<String> output = new ArrayList<>();

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);


                    output.add(temp[0]);
                } else
                    output.add(Character.toString(input[i]));
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return new PinyinVO(inputString,output);
    }

    
    
    /**
     * 调用第三方工具
     * 通过算法获取汉字的详细拼音
     * @param str1 原文本
     */
    public static TextPinyin getTextPinyin(String str1) throws Exception {

        //获取原字段的拼音，以“,”分割
        String str2 = getPinYinDetail(str1.replaceAll("[^\\u4e00-\\u9fa5]", ""));

        // 声母列表
        String initialConsonants = "b|p|d|t|g|k|zh|z|j|c|ch|q|f|sh|s|x|m|n|r|l|h|w|y";
        // 韵母列表
        String ymConsonants = "ang|eng|ong|ing|uan|an|en|in|un|ou|iu|ui|ao|ei|ie|ai|er|ue|e|a|o|i|u|ü";

        // 正则表达式，用于匹配声母
        String patternString_sm = "^(" + initialConsonants + ")(.*)$";
        String patterString_ym = "(" + ymConsonants + ")([1-5])";
        String patterString_ym2 = "(.)(" + ymConsonants + ")([1-5])";

        // 创建Pattern对象
        Pattern pattern_sm = Pattern.compile(patternString_sm);
        Pattern pattern_ym = Pattern.compile(patterString_ym);
        Pattern pattern_ym2 = Pattern.compile(patterString_ym2);

        //System.out.println(str2);
        String[] si_source = str2.split(",");

        List<String> sm_list = new ArrayList<>();
        List<String> ym_list = new ArrayList<>();
        List<String> sd_list = new ArrayList<>();

        for (String str:si_source)  {
            // 创建Matcher对象
            Matcher matcher_sm = pattern_sm.matcher(str);

            //System.out.println(str);

            String sm;
            String ym;
            String sd;
            String finalPart;

            if (matcher_sm.find()) {
                // 获取声母
                sm = matcher_sm.group(1);
                // 获取韵母
                finalPart = matcher_sm.group(2);


            }
            else {//没有声母
                sm = " ";
                finalPart = str;
                //throw new Exception("未找到声母");
            }
            Matcher matcher_ym = pattern_ym.matcher(finalPart);
            Matcher matcher_ym2 = pattern_ym2.matcher(finalPart);
            if(matcher_ym.find()){
                ym = matcher_ym.group(1);
                sd = matcher_ym.group(2);
            }
            else if (matcher_ym2.find()) {

                ym = matcher_ym2.group(2);
                sd = matcher_ym2.group(3);
            }
            else {
                throw new Exception("未找到韵母");
            }

            sm_list.add(sm);
            ym_list.add(ym);
            sd_list.add(sd);
        }

        return new TextPinyin(str2,sm_list,ym_list,sd_list);
    }


    /**
     * 详细比较两个文本的发音
     * @param source 原文本
     * @param pronounce 发音文本
     * @throws Exception 两个文本的长度可能不一样
     * @return 返回一个Result类型的表示结果
     */
    public static DysarthriaResultVO analysis(String source, String pronounce) throws Exception {
        
        TextPinyin source_pinyin = PinyinUtil.getTextPinyin(source);
        TextPinyin pronounce_pinyin = PinyinUtil.getTextPinyin(pronounce);

        //获取有序字符串数组
        String[] source_sm_arr= source_pinyin.getSm().toArray(new String[0]);
        String[] pronounce_sm_arr = pronounce_pinyin.getSm().toArray(new String[0]);

        String[] source_ym_arr = source_pinyin.getYm().toArray(new String[0]);
        String[] pronounce_ym_arr = pronounce_pinyin.getYm().toArray(new String[0]);

        String[] source_sd_arr = source_pinyin.getSd().toArray(new String[0]);
        String[] pronounce_sd_arr = pronounce_pinyin.getSd().toArray(new String[0]);
        

//        if(source_sm_arr.length != pronounce_sm_arr.length){
//            throw new Exception("长度不一！！！");
//
//        }

        int size = source_sm_arr.length; //字段长度

        int total_score = 0;//总分数

        //储存的比较结果
        List<SM_Enum> sm_result = new ArrayList<>();
        List<YM_Enum> ym_result = new ArrayList<>();
        List<SD_Enum> sd_result = new ArrayList<>();
        List<Integer> score_result = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {

            //这一段处理没有声母和字数不一的异常，比较的循环次数size以原字段为准
            int source_sm_num = 0;
            int pronounce_sm_num= 0;
            int source_ym_num = 0;
            int pronounce_ym_num = 0;
            String source_ym = " ";
            String pronounce_ym = " ";
            String source_sd = " ";
            String pronounce_sd = " ";


            try {
                source_sm_num = SM.get(source_sm_arr[i]);

            } catch (Exception e) {
                //throw new RuntimeException(e);
                if(e instanceof NullPointerException) {
                    System.out.println("原字无声母字");
                }
            }

            try{
                pronounce_sm_num = SM.get(pronounce_sm_arr[i]);
            }catch (Exception e){
                if(e instanceof NullPointerException) {
                    System.out.println("发音字无声母字");
                }
                if(e instanceof ArrayIndexOutOfBoundsException){
                    //发音字数过多
                    pronounce_sm_num = -1;
                }
            }

            try {
                pronounce_ym = pronounce_ym_arr[i];
                pronounce_sd = pronounce_sd_arr[i];
                pronounce_ym_num = YM.get(pronounce_ym_arr[i]);
            } catch (Exception e) {
                if(e instanceof ArrayIndexOutOfBoundsException) {
                    System.out.println("发音字数不足");
                    //发音字数不足
                    pronounce_ym_num = -1;
                }
            }

            source_ym_num = YM.get(source_ym_arr[i]);
            source_ym = source_ym_arr[i];
            source_sd  = source_sd_arr[i];
            int score = 0;

            //开始声母比较
            if(pronounce_sm_num == -1){
                sm_result.add(SM_Enum.SM_DIFFERENT);
                score += SM_RIGHT * 0;
            }
            else if(source_sm_num == pronounce_sm_num){
                sm_result.add(SM_Enum.SM_SAME);
                score += SM_RIGHT * 1 * (FULL_SCORE /TOTAL_RIGHT);
            }
            else if (source_sm_num != 0 && pronounce_sm_num != 0 ) {//确保两个都有声母
               if ((source_sm_num%9)==(pronounce_sm_num%9)) {
                    sm_result.add(SM_Enum.SM_DIFF_PART);
                    score += SM_RIGHT * SM_METHOD;
                }
                else if ((source_sm_num/9)==(pronounce_sm_num/9)) {
                    sm_result.add(SM_Enum.SM_DIFF_METHOD);
                    score += SM_RIGHT * SM_PART;
                }
               else {
                   sm_result.add(SM_Enum.SM_DIFFERENT);
                   score += SM_RIGHT * 0;
               }
            }
            else {
                sm_result.add(SM_Enum.SM_DIFFERENT);
                score += SM_RIGHT * 0;
            }


            //韵母比较

            if (pronounce_ym_num == -1){
                ym_result.add(YM_Enum.YM_DIFFERENT);
                score += YM_RIGHT * 0;
            }
            else {
                if(source_ym.equals(pronounce_ym)){
                    ym_result.add(YM_Enum.YM_SAME);
                    score += YM_RIGHT * 1 * (FULL_SCORE /TOTAL_RIGHT);
                }
                else if (source_ym_num == pronounce_ym_num) {
                    ym_result.add(YM_Enum.YM_SAME_LIKE);
                    score += YM_RIGHT * YM_ALIKE;
                }
                else if ((source_ym_num/100) == (pronounce_ym_num/100)) {
                    if((source_ym_num/4)==(pronounce_ym_num/4)){
                        ym_result.add(YM_Enum.YM_DIFF_SHAPE);
                        score += YM_RIGHT * YM_SHAPE;
                    }
                    else if((source_ym_num%4)==(pronounce_ym_num%4)){
                        ym_result.add(YM_Enum.YM_DIFF_SMOOTH);
                        score += YM_RIGHT * YM_STRUCT_AND_ROUGH;
                    }
                    else {
                        ym_result.add(YM_Enum.YM_DIFF_SHAPE_AND_SMOOTH);
                        score += YM_RIGHT * YM_ROUGH;
                    }
                }
                else if ((source_ym_num%4)== (pronounce_ym_num%4)) {
                    ym_result.add(YM_Enum.YM_DIFF_STRUCT);
                    score += YM_RIGHT * YM_STRUCT;
                }
                else {
                    ym_result.add(YM_Enum.YM_DIFFERENT);
                    score += YM_RIGHT * 0;
                }
            }

            //声调比较
            if(source_sd.equals(pronounce_sd)) {
                sd_result.add(SD_Enum.SD_SAME);
                score += SD_RIGHT * 1 * (FULL_SCORE /TOTAL_RIGHT);
            }
            else {
                sd_result.add(SD_Enum.SD_DIFFERENT);
                score += SD_RIGHT * 0;
            }

            score_result.add(score);

            //计算总分数
            total_score += score;
        }

        total_score = total_score/size;

        return new DysarthriaResultVO(sm_result,ym_result,sd_result,score_result,total_score);

    }
}
