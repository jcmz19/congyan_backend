package com.congyan.service;

import com.congyan.entity.dto.UserTrainData;
import com.congyan.entity.dto.UserTrainDataJson;
import com.congyan.entity.vo.request.UserReDataVO;

public interface UserDataService {

    void saveUserTrainData(int user_id, UserReDataVO userReDataVO)throws Exception;

    void insertTrainData(int user_id, UserTrainDataJson userTrainDataJson);
}
