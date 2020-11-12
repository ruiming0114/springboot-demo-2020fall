package com.example.demo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@ApiModel("Record记录实体类")
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @ApiModelProperty("记录id")
    private int rid;

    @ApiModelProperty("消息内容")
    private String message;

    @ApiModelProperty("记录时间")
    private Timestamp time;
}
