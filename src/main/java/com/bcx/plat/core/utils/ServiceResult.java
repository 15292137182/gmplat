package com.bcx.plat.core.utils;

import java.io.Serializable;

import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * 返回结果
 * Create By HCL at 2017/7/31
 */
public class ServiceResult implements Serializable {

    private static final long serialVersionUID = 812376774103405857L;

    private int state = STATUS_SUCCESS;
    private String message;
    private Object data;

    public ServiceResult() {

    }

    /**
     * 全参数构造方法
     *
     * @param state   状态
     * @param message 消息
     * @param data    数据
     */
    public ServiceResult(int state, String message, Object data) {

    }

    /**
<<<<<<< 75841a45d6d5882a644a27fdb9811d989f9bf428
=======
     * 接受消息和对象的构造函数
     * @param message
     * @param data
     */
    public ServiceResult(String message,Object data){
        state = STATUS_SUCCESS;
        this.data=data;
        this.message=message;
    }
    /**
>>>>>>> cd00a2c88c20081ad3302ccf0bebc5d353e95e67
     * 接受 data 的构造方法
     *
     * @param data 数据
     */
    public ServiceResult(Object data) {
        setData(data);
<<<<<<< 75841a45d6d5882a644a27fdb9811d989f9bf428
        message="";

    }

=======

                message="";

    }


>>>>>>> cd00a2c88c20081ad3302ccf0bebc5d353e95e67
    /**
     * 重写 toString 方法
     *
     * @return 返回 json 字符串
     */
    @Override
    public String toString() {
        return objToJson(this);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
