package com.congyan.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.congyan.entity.dto.Vowel;
import com.congyan.mapper.VowelMapper;
import com.congyan.service.VowelDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VowelDataServiceImpl extends ServiceImpl<VowelMapper, Vowel> implements VowelDataService {

    /**
     * 根据多个韵母字母查询具体发音
     * @param ym_list 韵母字母列表
     * @return 韵母发音列表
     */
    @Override
    public List<Vowel> getVowelDetailList(List<String> ym_list) {
        if (ym_list == null || ym_list.isEmpty()) {
            return Collections.emptyList();
        }
        List<Vowel> vowels = new ArrayList<>();
        for(String ym : ym_list){
            vowels.add(this.baseMapper.selectOne(Wrappers.<Vowel>query().eq("letter", ym)));
        }
        return vowels;
    }

    /**
     * 根据单个韵母字母查询具体发音
     * @param ym 韵母字母
     * @return 韵母发音
     */
    @Override
    public Vowel getOneVowelDetail(String ym) {
        return this.baseMapper.selectOne(Wrappers.<Vowel>query().eq("letter",ym));
    }
}
