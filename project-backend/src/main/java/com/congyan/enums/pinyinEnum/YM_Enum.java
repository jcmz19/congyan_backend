package com.congyan.enums.pinyinEnum;

import lombok.Getter;

@Getter
public enum YM_Enum {
    //完全相同
    YM_SAME(1,1,1),
    //发音方式和口型都相同
    YM_SAME_LIKE(0.75f,0.75f,0.75f),
    //发音结构相同，口型不同
    YM_DIFF_SHAPE(0.6f,1,0),
    //发音口型相同，结构不同
    YM_DIFF_STRUCT(0.6f,0,1),
    //发音口型相同且粗结构相同，发音细结构不同
    YM_DIFF_SMOOTH(0.4f,0.6f,1),
    //发音粗结构相同，口型不同
    YM_DIFF_SHAPE_AND_SMOOTH(0.2f,0.6f,0),
    //完全不同
    YM_DIFFERENT(0,0,0),
    ;

    private final float value;
    private final float struct_value;
    private final float shape_value;
    YM_Enum(float value,float struct_value,float shape_value) {
        this.value=value;
        this.struct_value = struct_value;
        this.shape_value = shape_value;
    }
}
