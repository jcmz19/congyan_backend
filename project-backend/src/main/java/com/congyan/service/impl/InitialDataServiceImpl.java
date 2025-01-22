package com.congyan.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.congyan.entity.dto.Initial;
import com.congyan.mapper.InitialMapper;
import com.congyan.service.InitialDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InitialDataServiceImpl extends ServiceImpl<InitialMapper, Initial> implements InitialDataService {


    /**
     * 根据多个声母字母查询具体发音
     * @param sm_list 声母字母列表
     * @return 声母详细发音列表
     */
    @Override
    public List<Initial> getInitialDetailList(List<String> sm_list) {
        if (sm_list == null || sm_list.isEmpty()) {
            return Collections.emptyList();
        }

        List<Initial> initials = new ArrayList<>();
        for(String sm : sm_list){
            initials.add(this.baseMapper.selectOne(Wrappers.<Initial>query().eq("letter", sm)));
        }
        return initials;
    }


    /**
     * 根据单个声母字母查询具体发音
     * @param sm 声母字母
     * @return 声母发音
     */
    @Override
    public Initial getOneInitialDetail(String sm) {
        return this.baseMapper.selectOne(Wrappers.<Initial>query().eq("letter",sm));
    }
}
