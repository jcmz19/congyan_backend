package com.congyan.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.congyan.entity.dto.TextPinyin;
import com.congyan.entity.dto.UserTrainData;
import com.congyan.entity.dto.UserTrainDataJson;
import com.congyan.entity.vo.request.UserReDataVO;
import com.congyan.mapper.UserTrainDataMapper;
import com.congyan.service.UserDataService;
import com.congyan.utils.PinyinUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDataServiceImpl extends ServiceImpl<UserTrainDataMapper,UserTrainData> implements UserDataService {


    /**
     * 保存用户数据服务
     * @param userReDataVO 请求数据
     * @throws Exception 数据库异常
     */
    @Override
    public void saveUserTrainData(int id,UserReDataVO userReDataVO) throws Exception {
        TextPinyin textPinyin = null;
        try {
            textPinyin = PinyinUtil.getTextPinyin(userReDataVO.getText());
        } catch (Exception e) {
            log.error("save problem1");
        }

        UserTrainDataJson userTrainDataJson = new UserTrainDataJson(textPinyin.getSm(),
                Arrays.asList(userReDataVO.getSm()),
                textPinyin.getYm(),
                Arrays.asList(userReDataVO.getYm()),
                textPinyin.getSd(),
                Arrays.asList(userReDataVO.getSd()));

        try {
            insertTrainData(id,userTrainDataJson);
        } catch (Exception e) {
            log.error("save problem");
        }

    }

    /**
     * 保存用户训练数据
     * @param user_id id
     * @param userTrainData Data
     */
    @Override
    public void insertTrainData(int user_id, UserTrainDataJson userTrainData) {
        this.save(new UserTrainData(null,user_id, JSON.toJSONString(userTrainData),null));
    }

}
