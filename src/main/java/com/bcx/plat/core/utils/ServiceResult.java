package com.bcx.plat.core.utils;

import com.bcx.plat.core.constants.SysMessage;

import java.io.Serializable;
import java.util.Map;

/**
 * Service 返回的结果
 * <p>
 * Create By HCL at 2017/7/31
 */
public class ServiceResult<T> implements Serializable {

    private static final long serialVersionUID = 812376774103405857L;

    private Map extra;
    private String respCode;
    private String respMsg;
    private PlatResult<T> content;


    /**
     * 空的构造方法，供 json 转换时使用
     * <p>
     * Create By HCL at 2017/8/7
     */
    public ServiceResult() {
    }

    /**
     * 全参数构造方法
     *  @param respCode    数据信息
     * @param respMsg   状态
     * @param content 消息
     */
    public ServiceResult(String respCode, String respMsg, PlatResult<T> content) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.content = content;
    }

    public static ServiceResult ErrorMsg(PlatResult content) {
        return new ServiceResult(SysMessage.SERVICE_RESPONSE_FAILED_CODE, SysMessage.SERVICE_RESPONSE_FAILED, content);
    }

    public static ServiceResult Msg(PlatResult content) {
        return new ServiceResult(SysMessage.SERVICE_RESPONSE_SUCCESSFUL_CODE, SysMessage.SERVICE_RESPONSE_SUCCESSFUL, content);
    }

    public Map getExtra() {
        return extra;
    }

    public void setExtra(Map extra) {
        this.extra = extra;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public PlatResult<T> getContent() {
        return content;
    }

    public void setContent(PlatResult<T> content) {
        this.content = content;
    }
}