package com.bcx.plat.core.utils;

import com.bcx.plat.core.constants.SysMessage;
import org.springframework.ui.ModelMap;

/**
 * Service 返回的结果
 * <p>
 * Create By HCL at 2017/7/31
 */
public class PlatResult extends ModelMap {

  private static final long serialVersionUID = 812376774103405857L;

  /**
   * 空的构造方法，供 json 转换时使用
   * <p>
   * Create By HCL at 2017/8/7
   */
  public PlatResult() {
  }

  /**
   * 全参数构造方法
   *
   * @param respCode 状态码
   * @param respMsg  返回消息
   * @param sr       数据
   */
  public PlatResult(String respCode, String respMsg, ServerResult sr) {
    addAttribute("respCode", respCode);
    addAttribute("respMsg", respMsg);
    addAttribute("content", sr);
  }

  /**
   * 返回成功的信息
   *
   * @param sr 服务层数据
   * @return 返回平台的结果信息
   */
  public static PlatResult success(ServerResult sr) {
    return new PlatResult(SysMessage.SERVICE_RESPONSE_SUCCESSFUL_CODE, SysMessage.SERVICE_RESPONSE_SUCCESSFUL, sr);
  }
}