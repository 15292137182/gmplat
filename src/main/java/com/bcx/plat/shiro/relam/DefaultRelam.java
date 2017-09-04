package com.bcx.plat.shiro.relam;

import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.TRUE_FLAG;
import static com.bcx.plat.core.utils.HexUtil.validPassword;
import static com.bcx.plat.core.utils.UtilsTool.getDateTimeNow;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 负责登陆和授权的 class
 * <p>
 * Create By HCL at 2017/8/14
 */
public class DefaultRelam extends AuthorizingRealm {

  @Autowired
  UserService userService;

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
      List<Map<String, Object>> userMaps = userService
              .select(new FieldCondition("id", Operator.EQUAL, userId));
      if (userMaps.size() == 1) {
        User user = new User().fromMap(userMaps.get(0));
        if (null != user) {
          String dbPassword = user.getPassword();
          if (validPassword(password, dbPassword)) {
            if (TRUE_FLAG.equals(user.getDisabled())) {
              // 账号被禁用
              throw new DisabledAccountException();
            } else {
              user.setLastLoginDate(getDateTimeNow());
              // 更新时间日期
//              userService.update(user.toMap(),
//                      new FieldCondition("rowId", Operator.EQUAL, user.getRowId()));
              // 将用户信息放入 session
              SecurityUtils.getSubject().getSession().setAttribute("user", user);
              return new SimpleAuthenticationInfo(userId, password, getName());
            }
          } else {
            // 账号密码错误
            throw new IncorrectCredentialsException();
          }
        } else {
          throw new UnknownAccountException();
        }
      } else {
        throw new UnknownAccountException();
      }
    }
    return null;
  }
}
