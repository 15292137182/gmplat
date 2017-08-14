package com.bcx.plat.shiro.relam;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 负责登陆和授权的 class
 *
 * Create By HCL at 2017/8/14
 */
public class DefaultRelam extends AuthorizingRealm {

  /**
   * 默认的授权方法
   *
   * @param principals 主要信息
   * @return 返回权限信息
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  /**
   * 默认的登陆认证方法
   *
   * @param token 主要凭证
   * @return 返回登陆信息
   * @throws AuthenticationException 认证异常
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String userId = (String) upToken.getPrincipal();
    String password = String.valueOf(upToken.getPassword());
    if (isValid(userId) && isValid(password)) {
      if (!"admin".equals(userId)) {
        throw new UnknownAccountException();
      } else if (!"admin".equals(password)) {
        throw new IncorrectCredentialsException();
      } else {
        return new SimpleAuthenticationInfo(userId, password, getName());
      }
    }
    return null;
  }
}
