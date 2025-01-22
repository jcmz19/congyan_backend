package com.congyan.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.congyan.entity.dto.TrainText;
import com.congyan.entity.vo.response.TrainTextVO;
import com.congyan.mapper.TrainTextMapper;
import com.congyan.service.TrainTextService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainTextServiceImpl extends ServiceImpl<TrainTextMapper, TrainText> implements TrainTextService{

    /**
     * 查询category分类的Text数据
     * @param category 枚举值
     * @return 数据List
     */
    @Override
    public List<TrainTextVO> getTrainTextByCategory(String category) {
        List<TrainText> trainTextlist = this.baseMapper.selectList(Wrappers.<TrainText>query().eq("category", category));
        List<TrainTextVO> trainTextVOList = new ArrayList<>();
        for(TrainText trainText: trainTextlist){
            trainTextVOList.add(trainText.asViewObject(TrainTextVO.class));
        }
        return trainTextVOList;
    }
}
