package com.bcx.plat.core.manager;

import com.bcx.plat.core.constants.Global;
import com.bcx.plat.core.entity.SystemSetting;
import com.bcx.plat.core.service.SystemSettingService;
import com.bcx.plat.core.utils.SpringContextHolder;

import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

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
  public static final String DEFAULT_PWD = "defaultPwd";

  public static final String UPLOAD_FILE_LIMIT = "uploadFileLimit";
  public static final String MIN_UPLOAD_FILE_SIZE = "minUploadFileSize";
  public static final String MAX_UPLOAD_FILE_SIZE = "maxUploadFileSize";
  public static final String UPLOAD_FILE_SUFFIX = "uploadFileSuffix";

  private static Map<String, Object> settingData;
  private static Map<String, Object> defaultSetting;

  /**
   * @return 默认密码
   */
  public static String getDefaultPwd() {
    return String.valueOf(getSettingValue(DEFAULT_PWD));
  }

  /**
   * 获取指定系统配置
   *
   * @param key key
   * @return 返回指定 key 的默认值，不存在返回 null
   */
  public static Object getSettingValue(String key) {
    return getSystemSetting().get(key);
  }

  /**
   * @return 有效的系统设置的键值
   */
  public static Set<String> getValidSettingKeys() {
    Set<String> keys = new HashSet<>();
    keys.add(SESSION_TIME_OUT);
    keys.add(LOGIN_FAILED_LIMIT);
    keys.add(PWD_SECURITY_DATE);
    keys.add(PWD_REQUIREMENTS);
    keys.add(MIN_PWD_LENGTH);
    keys.add(MAX_PWD_LENGTH);
    keys.add(DEFAULT_PWD);

    keys.add(UPLOAD_FILE_LIMIT);
    keys.add(MIN_UPLOAD_FILE_SIZE);
    keys.add(MAX_UPLOAD_FILE_SIZE);
    keys.add(UPLOAD_FILE_SUFFIX);
    return keys;
  }


  /**
   * @return 获取设置信息
   */
  public static Map<String, Object> getSystemSetting() {
    if (null == settingData) {
      settingData = new HashMap<>();
      getSystemSettingList().forEach(map -> settingData.put((String) map.get("key"), map.get("value")));
    }
    return settingData;
  }

  /**
   * 获取系统配置信息
   *
   * @return 返回
   */
  public static List<Map<String, Object>> getSystemSettingList() {
    List<Map<String, Object>> settingList = new ArrayList<>();

    SystemSettingService systemSettingService = SpringContextHolder.getBean(SystemSettingService.class);
    List<SystemSetting> settings = systemSettingService.selectAllValidSetting();
    getValidSettingKeys().forEach(key -> {
      Map<String, Object> setting = new HashMap<>();
      setting.put("key", key);
      settings.forEach(systemSetting -> {
        if (key.equals(systemSetting.getKey())) {
          setting.put("name", systemSetting.getName());
          setting.put("value", systemSetting.getValue());
          setting.put("remark", systemSetting.getRemark());
        }
      });
      fixSettingData(setting, key);
      if (setting.size() > 1) {
        settingList.add(setting);
      }
    });

    return settingList;
  }

  /**
   * @return 获取默认配置信息
   */
  public static Map<String, Object> getDefaultSettings() {
    if (defaultSetting == null) {
      defaultSetting = new HashMap<>();
      getValidSettingKeys().forEach(key -> {
        Object value = null;
        switch (key) {
          case SESSION_TIME_OUT:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + SESSION_TIME_OUT, -1);
            break;
          case PWD_SECURITY_DATE:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + PWD_SECURITY_DATE, -1);
            break;
          case LOGIN_FAILED_LIMIT:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + LOGIN_FAILED_LIMIT, -1);
            break;
          case PWD_REQUIREMENTS:
            value = Global.getValueAsString(PROPERTIES_KEY_PREFIX + PWD_REQUIREMENTS, "0000");
            break;
          case MIN_PWD_LENGTH:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + MIN_PWD_LENGTH, 1);
            break;
          case MAX_PWD_LENGTH:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + MAX_PWD_LENGTH, 64);
            break;
          case DEFAULT_PWD:
            value = Global.getValueAsString(PROPERTIES_KEY_PREFIX + DEFAULT_PWD, "123456");
            break;
          case UPLOAD_FILE_LIMIT:
            value = Global.getValueAsString(PROPERTIES_KEY_PREFIX + UPLOAD_FILE_LIMIT, "0");
            break;
          case MIN_UPLOAD_FILE_SIZE:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + MIN_UPLOAD_FILE_SIZE, -1);
            break;
          case MAX_UPLOAD_FILE_SIZE:
            value = Global.getValueAsInt(PROPERTIES_KEY_PREFIX + MAX_UPLOAD_FILE_SIZE, -1);
            break;
          case UPLOAD_FILE_SUFFIX:
            value = Global.getValueAsString(PROPERTIES_KEY_PREFIX + UPLOAD_FILE_SUFFIX, "");
            break;
        }
        defaultSetting.put(key, value);
      });
    }
    return defaultSetting;
  }

  /**
   * 补全设置信息
   *
   * @param key 需要补全的设置信息的 key
   */
  private static void fixSettingData(Map<String, Object> setting, String key) {
    // 开始补全信息
    if (getValidSettingKeys().contains(key)) {
      switch (key) {
        case SESSION_TIME_OUT:
          fixIfAbsent(setting, getDefaultSettings().get(key), "会话超时时间(分钟)", "-1 时会话将永不过期！");
          break;
        case PWD_SECURITY_DATE:
          fixIfAbsent(setting, getDefaultSettings().get(key), "密码安全时间(月)", "-1 时不限制");
          break;
        case LOGIN_FAILED_LIMIT:
          fixIfAbsent(setting, getDefaultSettings().get(key), "每日允许登录失败次数", "-1 时不限制");
          break;
        case PWD_REQUIREMENTS:
          fixIfAbsent(setting, getDefaultSettings().get(key), "密码要求", "默认无任何要求");
          break;
        case MIN_PWD_LENGTH:
          fixIfAbsent(setting, getDefaultSettings().get(key), "密码最小长度", "最小长度为 1 ~");
          break;
        case DEFAULT_PWD:
          fixIfAbsent(setting, getDefaultSettings().get(key), "默认密码", "");
          break;
        case MAX_PWD_LENGTH:
          fixIfAbsent(setting, getDefaultSettings().get(key), "密码最大长度", "最大长度为 64 ~");
          break;
        case UPLOAD_FILE_LIMIT:
          fixIfAbsent(setting, getDefaultSettings().get(key), "上传文件限制", "1：开启限制；0：关闭限制");
          break;
        case MIN_UPLOAD_FILE_SIZE:
          fixIfAbsent(setting, getDefaultSettings().get(key), "最小文件大小（KB）", "最小允许上传的文件大小，-1 不限制;默认不限制");
          break;
        case MAX_UPLOAD_FILE_SIZE:
          fixIfAbsent(setting, getDefaultSettings().get(key), "最大文件大小（KB）", "最大允许上传的文件大小，-1 不限制；默认不限制");
          break;
        case UPLOAD_FILE_SUFFIX:
          fixIfAbsent(setting, getDefaultSettings().get(key), "允许上传文件后缀名"
                  , "[正则表达式] （例如需要上传的文件后缀名为 txt 或者 mp3 ： ^txt|mp3$；对上传的文件名没有任何限制则留空或者写为 ： ^.*$）");
          break;
      }
    }
  }

  private static void fixIfAbsent(Map<String, Object> setting, Object value, String name, String explain) {
    putIfInvalid(setting, "value", value);
    putIfInvalid(setting, "name", name);
    putIfInvalid(setting, "explain", explain);
  }

  /**
   * 如果值无效就填入
   *
   * @param map   map
   * @param key   key
   * @param value 值
   */
  private static void putIfInvalid(Map<String, Object> map, String key, Object value) {
    if (!isValid(map.get(key)) && null != value) {
      map.put(key, value);
    }
  }

}