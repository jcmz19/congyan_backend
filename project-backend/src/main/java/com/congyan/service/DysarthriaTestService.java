package com.congyan.service;

import com.congyan.entity.dto.PinyinDetail;
import com.congyan.entity.vo.response.*;

import java.io.File;

public interface DysarthriaTestService {

    DysarthriaResultVO getDysarthriaResult(String text, File pronounceFile) throws Exception;

    PinyinDetailVO getPinyinDetailVO(String text) throws Exception;
    PinyinDetail getPinyinDetail(String text)throws Exception;

    PinyinVO getPinyin(String text);

    TtsVO getTTS(String text) throws Exception;

    SinglePronounceDetailVO getSinglePronounceDetail(char character,File temFile) throws Exception;


}
