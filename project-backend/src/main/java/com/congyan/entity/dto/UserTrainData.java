package com.congyan.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("user_train_data")
@AllArgsConstructor
public class UserTrainData {
    @TableId(type = IdType.AUTO)
    Integer id;

    int userId;

    String train_data;

    @TableField(exist = false)
    Account account;
}
