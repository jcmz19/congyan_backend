package com.congyan.entity.dto;

import com.congyan.enums.pinyinEnum.SD_Enum;
import com.congyan.enums.pinyinEnum.SM_Enum;
import com.congyan.enums.pinyinEnum.YM_Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Data
public class UserTrainDataJson {
    List<String> sm;
    List<String> ym;
    List<String> sd;
    List<SM_Enum> sm_enums;

    List<YM_Enum> ym_enums;

    List<SD_Enum> sd_enums;

}
