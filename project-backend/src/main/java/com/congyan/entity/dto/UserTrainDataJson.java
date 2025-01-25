package com.congyan.entity.dto;

import com.congyan.enums.pinyinEnum.SD_Enum;
import com.congyan.enums.pinyinEnum.SM_Enum;
import com.congyan.enums.pinyinEnum.YM_Enum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserTrainDataJson {
    List<String> sm;
    List<SM_Enum> sm_enums;
    List<String> ym;
    List<YM_Enum> ym_enums;
    List<String> sd;
    List<SD_Enum> sd_enums;

}
