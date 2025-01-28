package com.congyan.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.congyan.entity.dto.TextPinyin;
import com.congyan.entity.dto.UserTrainData;
import com.congyan.entity.dto.UserTrainDataJson;
import com.congyan.entity.vo.request.UserReDataVO;
import com.congyan.entity.vo.response.UserTrainSummaryAnalysisVO;
import com.congyan.mapper.UserTrainDataMapper;
import com.congyan.service.UserDataService;
import com.congyan.utils.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class UserDataServiceImpl extends ServiceImpl<UserTrainDataMapper,UserTrainData> implements UserDataService {

    @Value("${dysarthria.calculate.time-lambda}")
    float TIME_LAMBDA;
    @Value("${dysarthria.score-right.summary-analysis.method}")
    int METHOD_RIGHT;
    @Value("${dysarthria.score-right.summary-analysis.part}")
    int PART_RIGHT;
    @Value("${dysarthria.score-right.summary-analysis.struct}")
    int STRUCT_RIGHT;
    @Value("${dysarthria.score-right.summary-analysis.shape}")
    int SHAPE_RIGHT;
    @Value("${dysarthria.score-right.summary-analysis.sd}")
    int SD_RIGHT;
    @Value("${dysarthria.score-right.summary-analysis.total}")
    int TOTAL_RIGHT;


    /**
     * 保存用户数据服务
     * @param userReDataVO 请求数据
     * @throws Exception 拼音解析异常
     */
    @Override
    public void saveUserTrainData(int id,UserReDataVO userReDataVO) throws Exception {
        TextPinyin textPinyin = null;

        textPinyin = PinyinUtil.getTextPinyin(userReDataVO.getText());

        UserTrainDataJson userTrainDataJson = new UserTrainDataJson(textPinyin.getSm(),
                textPinyin.getYm(),
                textPinyin.getSd(),
                Arrays.asList(userReDataVO.getSm()),
                Arrays.asList(userReDataVO.getYm()),
                Arrays.asList(userReDataVO.getSd()));
        insertTrainData(id,userTrainDataJson);
    }

    /**
     * 保存用户训练数据
     * @param user_id id
     * @param userTrainData Data
     *
     */
    @Override
    public void insertTrainData(int user_id, UserTrainDataJson userTrainData) {
        this.save(new UserTrainData(null,user_id, JSON.toJSONString(userTrainData),new Date(System.currentTimeMillis()),null));
    }


    /**
     * 从数据库中查询用户数据，
     * 对数据进行指数衰减的计算
     * @param user_id 用户id
     * @return 返回发音方式、发音部位、发音结构、发音口型以及声调的综合评分
     */
    @Override
    public UserTrainSummaryAnalysisVO userTrainDataSummaryAnalysis(Integer user_id) {
        List<UserTrainData> userTrainDataList =
        this.baseMapper.selectList(new QueryWrapper<UserTrainData>().eq("user_id",user_id));
        return userTrainDataSummaryAnalysis(userTrainDataList);
    }

    /**
     * 对数据进行指数衰减的计算
     * @param userTrainDataList 具体数据列表
     * @return 返回发音方式、发音部位、发音结构、发音口型以及声调的综合评分
     */
    @Override
    public UserTrainSummaryAnalysisVO userTrainDataSummaryAnalysis(List<UserTrainData> userTrainDataList) {

        //排序
        userTrainDataList.sort(Comparator.comparing(UserTrainData::getTime));

        long lastTime = userTrainDataList.get(0).getTime().getTime();
        double right_total = 0;


        float method_score_total = 0;
        float part_score_total = 0;
        float struct_score_total = 0;
        float shape_score_total = 0;
        float sd_score_total = 0;
        for (UserTrainData userTrainData:userTrainDataList){

            UserTrainDataJson userTrainDataJson= JSON.parseObject(userTrainData.getTrainData(),UserTrainDataJson.class);
            //计算相差分钟数
            long timeDiff = (userTrainData.getTime().getTime() - lastTime)/1000*60;
            //计算时间权重
            double right = Math.exp(-timeDiff * TIME_LAMBDA);
            right_total += right;


            int each_length = userTrainDataJson.getSm().size();
            //获取每一条数据的分数
            float each_method_total = 0;
            float each_part_total = 0;
            float each_struct_total = 0;
            float each_shape_total = 0;
            float each_sd_total = 0;
            for (int i = 0; i < each_length; i++) {
                each_method_total+=userTrainDataJson.getSm_enums().get(i).getMethod_value();
                each_part_total+=userTrainDataJson.getSm_enums().get(i).getPart_value();
                each_struct_total+= userTrainDataJson.getYm_enums().get(i).getStruct_value();
                each_shape_total+= userTrainDataJson.getYm_enums().get(i).getShape_value();
                each_sd_total += userTrainDataJson.getSd_enums().get(i).getValue();
            }

            method_score_total += (float) (right * each_method_total/each_length);
            part_score_total +=  (float) (right * each_part_total/each_length);
            struct_score_total += (float) (right * each_struct_total/each_length);
            shape_score_total += (float) (right * each_shape_total/each_length);
            sd_score_total += (float) (right * each_sd_total/each_length);

        }
        float method_score = (float) (method_score_total/right_total);
        float part_score = (float) (part_score_total/right_total);
        float struct_score = (float) (struct_score_total/right_total);
        float shape_score = (float) (shape_score_total/right_total);
        float sd_score = (float) (sd_score_total/right_total);

        float summary_score = (method_score*METHOD_RIGHT
                +part_score*PART_RIGHT
                +struct_score*STRUCT_RIGHT
                +shape_score*SHAPE_RIGHT
                +sd_score*SD_RIGHT) / TOTAL_RIGHT;
        log.info(String.valueOf(METHOD_RIGHT));
        return new UserTrainSummaryAnalysisVO(method_score,part_score,struct_score,shape_score,sd_score,summary_score);
    }

}
