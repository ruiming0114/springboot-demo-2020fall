package com.example.demo.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Json数据实体类")
public class JsonResult<T> {

    @ApiModelProperty("返回数据")
    private T data;

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("返回消息")
    private String msg;

    /**
     * 若没有数据返回，默认状态码为 0，提示信息为“success”
     */
    public JsonResult() {
        this.code = 0;
        this.msg = "success";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     */
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为 0，默认提示信息为“success”
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = 0;
        this.msg = "success";
    }

    /**
     * 有数据返回，人为指定状态码和信息
     */
    public JsonResult(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}

