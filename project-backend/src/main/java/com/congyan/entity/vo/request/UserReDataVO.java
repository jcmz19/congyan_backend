package com.congyan.entity.vo.request;


import com.congyan.enums.pinyinEnum.SD_Enum;
import com.congyan.enums.pinyinEnum.SM_Enum;
import com.congyan.enums.pinyinEnum.YM_Enum;
import lombok.Data;

@Data
public class UserReDataVO {
    String text;
    SM_Enum[] sm;
    YM_Enum[] ym;
    SD_Enum[] sd;
}
