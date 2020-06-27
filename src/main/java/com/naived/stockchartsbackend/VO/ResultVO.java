package com.naived.stockchartsbackend.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultVO {

    public static int SUCCESS = 0;
    public static int GENERAL_FAILED = 1;

    /*
    0代表成功，1代表失败
     */
    int result;
    //内容
    Object data;
    /*
    返回的信息
     */
    String message;

    public static ResultVO SUCCESS(Object data){
        return new ResultVO(ResultVO.SUCCESS, data, "");
    }

    public static ResultVO FAILED(int state, Object data, String message){
        return new ResultVO(state, data, message);
    }

    public static ResultVO FAILED(Object data){
        return new ResultVO(ResultVO.GENERAL_FAILED, data, "");
    }

    public static ResultVO FAILED(Object data, String message){
        return new ResultVO(ResultVO.GENERAL_FAILED, data, message);
    }

    public static ResultVO FAILED(int state, String message){
        return new ResultVO(state, null, message);
    }
}
