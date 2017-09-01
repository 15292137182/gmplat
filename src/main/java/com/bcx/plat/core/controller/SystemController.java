package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * Create By HCL at 2017/8/14
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/system")
public class SystemController extends BaseController {

  /**
   * 登陆请求
   *
   * @param request 请求
   * @return 返回
   */
  @RequestMapping("/login")
  public ServiceResult login(HttpServletRequest request) {
    PlatResult platResult = new PlatResult();
    String userId = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordToken token = new UsernamePasswordToken(userId, password);
    Subject subject = SecurityUtils.getSubject();
    platResult.setState(STATUS_FAIL);
    // 开始登陆认证
    try {
      subject.login(token);
      platResult.setState(STATUS_SUCCESS);
      platResult.setMsg("登陆成功！");
    } catch (IncorrectCredentialsException e) {
      platResult.setMsg("帐号密码不匹配 ~");
      logger.warn(platResult.getMsg() + " : " + userId + " | " + password + "\n" + e.getMessage());
    } catch (DisabledAccountException e) {
      platResult.setMsg("帐号已被禁用 ~ ");
      logger.warn(platResult.getMsg() + " : " + userId + " | " + password + "\n" + e.getMessage());
    } catch (UnknownAccountException e) {
      platResult.setMsg("账号不存在 ~ ");
      logger.warn(platResult.getMsg() + " : " + userId + " | " + password + "\n" + e.getMessage());
    } catch (Exception e) {
      platResult.setMsg("系统内部响应异常，请稍后重试 ~ ");
      logger.error(platResult.getMsg() + " : " + userId + " | " + password + "\n" + e.getMessage());
    }

    return ServiceResult.Msg(platResult);
  }

}
