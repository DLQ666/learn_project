package com.learn.utils.result;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: learn_parent
 * @description: 统一返回响应结果
 * @author: Hasee
 * @create: 2020-06-14 15:14
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class ResponseResult {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 构造方法私有化
     */
    private ResponseResult(){}

    /**
     * 成功方法
     * @return
     */
    public static ResponseResult ok(){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        responseResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        responseResult.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return responseResult;
    }

    /**
     * 失败方法
     * @return
     */
    public static ResponseResult error(){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        responseResult.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        responseResult.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return responseResult;
    }

    public static ResponseResult setResult(ResultCodeEnum resultCodeEnum){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(resultCodeEnum.getSuccess());
        responseResult.setCode(resultCodeEnum.getCode());
        responseResult.setMessage(resultCodeEnum.getMessage());
        return responseResult;
    }

    public ResponseResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResponseResult message(String message){
        this.setMessage(message);
        return this;
    }

    public ResponseResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResponseResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResponseResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
