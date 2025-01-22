package com.congyan.entity.dto;

import com.congyan.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pronounce implements BaseData {
    String sd;
    Initial initial_detail;
    Vowel vowel_detail;
}
