package com.congyan.entity.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TtsTextVO {
    @Length(min = 1)
    String text;
}
