package com.congyan.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTrainSummaryAnalysisVO {
    //发音方法评分
    float method_score;
    //发音部位评分
    float part_score;
    //发音结构评分
    float struct_score;
    //发音口型评分
    float shape_score;
    //发音声调评分
    float sd_score;
    //总评分
    float total_score;
}
