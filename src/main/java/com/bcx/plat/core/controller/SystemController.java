package com.bcx.plat.core.controller;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.utils.ServiceResult;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By HCL at 2017/8/14
 */
@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {

  /**
   * 登陆请求
   *
   * @param request 请求
   * @return 返回
   */
  @RequestMapping("/login")
  public ServiceResult login(HttpServletRequest request) {
    ServiceResult sr = new ServiceResult();
    String userId = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordToken token = new UsernamePasswordToken(userId, password);
    Subject subject = SecurityUtils.getSubject();
    sr.setState(STATUS_FAIL);
    // 开始登陆认证
    try {
      subject.login(token);
      sr.setState(STATUS_SUCCESS);
      sr.setMessage("登陆成功！");
    } catch (IncorrectCredentialsException e) {
      sr.setMessage("帐号密码不匹配 ~");
      logger.warn(sr.getMessage() + " : " + userId + " | " + password + "\n" + e.getMessage());
    } catch (DisabledAccountException e) {
      sr.setMessage("帐号已被禁用 ~ ");
      logger.warn(sr.getMessage() + " : " + userId + " | " + password + "\n" + e.getMessage());
    } catch (UnknownAccountException e) {
      sr.setMessage("账号不存在 ~ ");
      logger.warn(sr.getMessage() + " : " + userId + " | " + password + "\n" + e.getMessage());
    } catch (Exception e) {
      sr.setMessage("系统内部响应异常，请稍后重试 ~ ");
      logger.error(sr.getMessage() + " : " + userId + " | " + password + "\n" + e.getMessage());
    }
    return sr;
  }

}
