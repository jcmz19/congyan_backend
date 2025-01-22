package com.congyan.entity.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("vowels")
public class Vowel {

    @TableId(type = IdType.AUTO)
    Integer id;
    String letter;
    String pronunciationType;
    String roughStructure;
    String smoothStructure;


}
