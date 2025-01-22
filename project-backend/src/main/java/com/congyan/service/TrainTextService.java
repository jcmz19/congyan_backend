package com.congyan.service;

import com.congyan.entity.vo.response.TrainTextVO;

import java.util.List;

public interface TrainTextService {
    List<TrainTextVO> getTrainTextByCategory(String category);
}
