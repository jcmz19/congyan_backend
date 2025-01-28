package com.congyan.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@TableName("user_train_data")
@AllArgsConstructor
public class UserTrainData {
    @TableId(type = IdType.AUTO)
    Integer id;

    Integer userId;

    @TableField(value = "train_data")
    String trainData;

    Date time;

    @TableField(exist = false)
    Account account;

    public UserTrainData() {
    }
}
