package com.bcx.plat.core.manager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 系统设置管理类
 * <p>
 * Create By HCL at 2017/10/11
 */
public abstract class SystemSettingManager {

  public static final String PROPERTIES_KEY_PREFIX = "web.";

  public static final String SESSION_TIME_OUT = "sessionTimeout"; // 会话超时时间(分钟)
  public static final String PWD_SECURITY_DATE = "pwdSecurityDate"; // 密码安全时间
  public static final String LOGIN_FAILED_LIMIT = "loginFailedLimit";  // 登录失败次数限制
  public static final String PWD_REQUIREMENTS = "pwdRequirements";  // 密码要求表达式
  public static final String MIN_PWD_LENGTH = "minPwdLength";
  public static final String MAX_PWD_LENGTH = "maxPwdLength";

  public static final String UPLOAD_FILE_LIMIT = "uploadFileLimit";
  public static final String MIN_UPLOAD_FILE_SIZE = "minUploadFileSize";
  public static final String MAX_UPLOAD_FILE_SIZE = "maxUploadFileSize";
  public static final String UPLOAD_FILE_SUFFIX = "uploadFileSuffix";

  public static Map<String, Object> settingData;

  /**
   * @return 有效的系统设置的键值
   */
  public static Set<String> getValidSettingKeys() {
    Set<String> keys = new HashSet<>();
    keys.add(SESSION_TIME_OUT);
    keys.add(PWD_SECURITY_DATE);
    keys.add(LOGIN_FAILED_LIMIT);
    keys.add(PWD_REQUIREMENTS);
    keys.add(MIN_PWD_LENGTH);
    keys.add(MAX_PWD_LENGTH);
    keys.add(UPLOAD_FILE_LIMIT);
    keys.add(MIN_UPLOAD_FILE_SIZE);
    keys.add(MAX_UPLOAD_FILE_SIZE);
    keys.add(UPLOAD_FILE_SUFFIX);
    return keys;
  }

}