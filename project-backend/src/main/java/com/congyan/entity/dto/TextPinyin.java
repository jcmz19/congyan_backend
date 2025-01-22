package com.congyan.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TextPinyin{
    String text;
    List<String> sm;
    List<String> ym;
    List<String> sd;

}
