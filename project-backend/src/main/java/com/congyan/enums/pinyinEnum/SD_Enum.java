package com.congyan.enums.pinyinEnum;

import lombok.Getter;

@Getter
public enum SD_Enum {
    //发音相同
    SD_SAME(1),
    //发音不同
    SD_DIFFERENT(0);


    private final float value;
    SD_Enum(float i) {
        this.value=i;
    }
}
