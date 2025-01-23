package com.congyan.controller;

import com.congyan.entity.RestBean;
import com.congyan.entity.vo.response.TrainTextVO;
import com.congyan.service.TrainTextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/trainText")
@Tag(name = "获取训练文本",description = "根据不同的分类获取文本数据")
public class TrainTextController {
    @Resource
    TrainTextService trainTextService;

    /**
     * 按文章分类获取文本数据
     * @param category 分类枚举值
     * @return 此文本标签下的所有文章
     */
    @GetMapping("/getTrainTextByCategory")
    @Operation(summary = "按文章分类获取")
    public RestBean<List<TrainTextVO>> getTrainTextByCategory(@RequestParam String category){

        if(category.matches("^(ancient poetry|modern poetry|prose)$"))
            return RestBean.success(trainTextService.getTrainTextByCategory(category));
        else
            return RestBean.failure(2006,"没有此目录");
    }
}
