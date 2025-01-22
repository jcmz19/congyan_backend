package com.congyan;

import com.congyan.entity.dto.Initial;
import com.congyan.entity.dto.PinyinDetail;
import com.congyan.entity.dto.TextPinyin;
import com.congyan.entity.dto.Vowel;
import com.congyan.entity.vo.response.PinyinDetailVO;
import com.congyan.service.InitialDataService;
import com.congyan.service.VowelDataService;
import com.congyan.utils.PinyinUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;

@SpringBootTest
class MyProjectBackendApplicationTests {

    public static void main(String[] args) throws Exception {
        System.out.println(PinyinUtil.getTextPinyin("鱼"));
    }
}

@Service
class TestMain{
    @Resource
    InitialDataService initialService;
    @Resource
    VowelDataService vowelDataServiceImpl ;

    public void test1() {

        TextPinyin textPinyin = null;
        try {
            System.out.println(PinyinUtil.getTextPinyin("葡"));
            textPinyin = PinyinUtil.getTextPinyin("你");
        } catch (Exception e) {
            System.out.println("wan");
        }

        List<Initial> initialDetailList = initialService.getInitialDetailList(textPinyin.getSm());
        List<Vowel> vowelDetailList = vowelDataServiceImpl.getVowelDetailList(textPinyin.getYm());;


        try {

            new PinyinDetail(textPinyin, initialDetailList, vowelDetailList).asViewObject(PinyinDetailVO.class);
        } catch (Exception e) {
            System.out.println("2");
        }
    }
}
