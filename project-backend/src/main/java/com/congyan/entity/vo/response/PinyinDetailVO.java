package com.congyan.entity.vo.response;

import com.congyan.entity.dto.Initial;
import com.congyan.entity.dto.TextPinyin;
import com.congyan.entity.dto.Vowel;
import lombok.Data;

import java.util.List;


@Data
public class PinyinDetailVO {
    TextPinyin textPinyin;
    List<Initial> sm_detail;
    List<Vowel> ym_detail;
}
