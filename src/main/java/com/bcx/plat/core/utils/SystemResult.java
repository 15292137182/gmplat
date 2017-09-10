package com.bcx.plat.core.utils;

import com.bcx.plat.core.constants.SysMessage;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service 返回的结果
 * <p>
 * Create By HCL at 2017/7/31
 */
public class SystemResult {

  private Map<String, Object> resp = new HashMap<>();

  /**
   * 空的构造方法，供 json 转换时使用
   * <p>
   * Create By HCL at 2017/8/7
   */
  public SystemResult() {
  }

  /**
   * 全参数构造方法
   *
   * @param respCode 状态码
   * @param respMsg  返回消息
   * @param sr       数据
   */
  public SystemResult(String respCode, String respMsg, ServerResult sr) {
    resp.put("respCode", respCode);
    resp.put("respMsg", respMsg);
    resp.put("content", sr);
  }


  /**
   * 返回成功的信息
   *
   * @param sr 服务层数据
   * @return 返回平台的结果信息
   */
  public static SystemResult success(ServerResult sr) {
    return new SystemResult(SysMessage.SERVICE_RESPONSE_SUCCESSFUL_CODE, SysMessage.SERVICE_RESPONSE_SUCCESSFUL, sr);
  }

  public Map<String, Object> getResp() {
    return resp;
  }

  public void setResp(Map<String, Object> resp) {
    this.resp = resp;
  }
}