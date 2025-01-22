package com.congyan.enums.pinyinEnum;

public enum YM_Enum {
    //完全相同
    YM_SAME,
    //发音方式和口型都相同
    YM_SAME_LIKE,
    //发音结构相同，口型不同
    YM_DIFF_SHAPE,
    //发音口型相同，结构不同
    YM_DIFF_STRUCT,
    //发音口型相同且粗结构相同，发音细结构不同
    YM_DIFF_SMOOTH,
    //发音粗结构相同，口型不同
    YM_DIFF_SHAPE_AND_SMOOTH,
    //完全不同
    YM_DIFFERENT,
}
