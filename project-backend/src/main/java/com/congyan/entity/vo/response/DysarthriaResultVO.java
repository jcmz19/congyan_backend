package com.congyan.entity.vo.response;


import com.congyan.enums.pinyinEnum.SD_Enum;
import com.congyan.enums.pinyinEnum.SM_Enum;
import com.congyan.enums.pinyinEnum.YM_Enum;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

@Data
@AllArgsConstructor
public class DysarthriaResultVO {

    private List<SM_Enum> sm;
    private List<YM_Enum> ym;
    private List<SD_Enum> sd;
    private List<Integer> single_score;
    private int total_score;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(SM_Enum e:sm){
            sb.append(e).append(".");
        }
        sb.append("\n");
        for(YM_Enum e:ym){
            sb.append(e).append(".");
        }
        sb.append("\n");
        for(SD_Enum e:sd){
            sb.append(e).append(".");
        }
        sb.append("\n");
        sb.append(total_score);
        return sb.toString();
    }
}
