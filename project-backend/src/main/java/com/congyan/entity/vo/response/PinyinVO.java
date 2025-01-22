package com.congyan.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PinyinVO {
    String text;
    List<String> pinyin;

}
