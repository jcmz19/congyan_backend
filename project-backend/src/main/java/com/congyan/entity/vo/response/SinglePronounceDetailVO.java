package com.congyan.entity.vo.response;

import com.congyan.entity.dto.Initial;
import com.congyan.entity.dto.Vowel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SinglePronounceDetailVO {

    String sd;
    Initial initial_detail;
    Vowel vowel_detail;
}
