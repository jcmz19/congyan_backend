package com.congyan.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("initials")
public class Initial {

    @TableId(type = IdType.AUTO)
    Integer id;

    String letter;
    String articulationPoint;
    String pronunciationMethod;


}
