package com.congyan.service;

import com.congyan.entity.dto.Initial;

import java.util.List;

public interface InitialDataService {

    List<Initial> getInitialDetailList(List<String> sm_list);

    Initial getOneInitialDetail(String sm);
}
