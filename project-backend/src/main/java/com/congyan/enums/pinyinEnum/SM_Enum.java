package com.congyan.enums.pinyinEnum;

import lombok.Getter;

@Getter
public enum SM_Enum {
    //声母相同
    SM_SAME(1,1,1),
    //声母发音部位不同
    SM_DIFF_PART(0.6F,1,0),
    //声母发音方式不同
    SM_DIFF_METHOD(0.4f,0,1),
    //发音完全不同
    SM_DIFFERENT(0,0,0),
    ;
    private final float value;
    private final float method_value;
    private final float part_value;


    SM_Enum(float value, float method_value, float part_value) {
        this.value = value;
        this.method_value = method_value;
        this.part_value = part_value;
    }
}
