package com.congyan.service;

import com.congyan.entity.dto.Vowel;

import java.util.List;

public interface VowelDataService {

    List<Vowel> getVowelDetailList(List<String> ym_list);

    Vowel getOneVowelDetail(String ym);
}
