package com.congyan.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.congyan.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("train_text")
@AllArgsConstructor
public class TrainText implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    String title;
    String author;
    String text;
    String category;
    String suggestedDuration;
    String applicablePeople;
    String grade;

}
