package com.congyan.entity.dto;

import com.congyan.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PinyinDetail implements BaseData {
    TextPinyin textPinyin;
    List<Initial> sm_detail;
    List<Vowel> ym_detail;
}
