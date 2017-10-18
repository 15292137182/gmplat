package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.manager.SystemSettingManager;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.UserService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 用户信息controller层
 * Created by YoungerOu on 2017/10/10.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/user")
public class UserController extends BaseController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 人员信息 - 查询方法
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询(json)
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    ServerResult result = userService.queryPage(search, param, pageNum, pageSize, order);
    return result(result);
  }

  /**
   * 人员信息 - 根据指定的一个或多个组织机构查询
   *
   * @param param 组织机构参数，以数组的形式传入["1","2"]
   * @return PlatResult
   */
  @RequestMapping("/queryByOrg")
  public PlatResult queryByOrg(String param, Integer pageNum, Integer pageSize) {
    if (UtilsTool.isValid(param)) {
      List list = UtilsTool.jsonToObj(param, List.class);
      ServerResult serverResult = userService.queryByOrg(list, pageNum, pageSize);
      return result(serverResult);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 人员信息 - 根据指定字段精确查询
   *
   * @param param 接收指定参数(rowId, id, name...)
   * @return PlatResult
   */
  @RequestMapping("/queryBySpecify")
  public PlatResult queryBySpecify(@RequestParam Map<String, Object> param) {
    if (!param.isEmpty()) {
      Condition condition = UtilsTool.convertMapToAndCondition(User.class, param);
      List<Map> select = userService.selectMap(condition);
      if (!select.isEmpty()) {
        return successData(Message.QUERY_SUCCESS, select);
      } else {
        return fail(Message.QUERY_FAIL);
      }
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 人员信息 - 新增
   *
   * @param param 接收一个实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult add(@RequestParam Map<String, Object> param) {
    Object id = param.get("id");
    Object name = param.get("name");
    if (null != id && !"".equals(id.toString().trim())
            && null != name && !"".equals(name.toString().trim())) {//工号和姓名不能为空

      //根据工号查询是否已存在该工号的记录
      Condition validCondition = new ConditionBuilder(User.class).and().equal("id", id.toString().trim()).endAnd().buildDone();
      List<User> list = userService.select(validCondition);
      if (list.isEmpty()) {
        param.put("id", id.toString().trim());
        param.put("name", name.toString().trim());

        //从系统设置获取密码强度、长度校验的规则 进行校验
        String password = String.valueOf(param.get("password"));
        if (validPassword(password)) {
          param.put("passwordUpdateTime", UtilsTool.getDateTimeNow());
          param.put("status", BaseConstants.IN_USE);
          User user = new User().buildCreateInfo().fromMap(param);
          if (user.insert() != -1) {
            return successData(Message.NEW_ADD_SUCCESS, user);
          } else {
            return fail(Message.NEW_ADD_FAIL);
          }
        } else {
          return fail(Message.NEW_ADD_FAIL);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      return fail(Message.DATA_CANNOT_BE_EMPTY);
    }

  }

  /**
   * 校验密码长度及强度
   *
   * @param password 要校验的密码
   * @return true or false 密码是否符合要求
   */
  private boolean validPassword(String password) {
    int minPwdLength = Integer.parseInt(SystemSettingManager.getSettingValue(SystemSettingManager.MIN_PWD_LENGTH).toString());
    int maxPwdLength = Integer.parseInt(SystemSettingManager.getSettingValue(SystemSettingManager.MAX_PWD_LENGTH).toString());
    if (password.length() >= minPwdLength && password.length() <= maxPwdLength) {//密码长度符合要求
      //验证密码强度
      String pwdRequirements = SystemSettingManager.getSettingValue("pwdRequirements").toString();
      if (!pwdRequirements.equals("0000")) {
        String upperCase = pwdRequirements.substring(0, 1);
        String lowercase = pwdRequirements.substring(1, 2);
        String num = pwdRequirements.substring(2, 3);
        String other = pwdRequirements.substring(3, 4);
        String regex = "(?:";
        if (upperCase.equals("1")) {
          regex += "(?=.*[A-Z])";
        }
        if (lowercase.equals("1")) {
          regex += "(?=.*[a-z])";
        }
        if (num.equals("1")) {
          regex += "(?=.*[0-9])";
        }
        if (other.equals("1")) {
          regex += "(?=.*[^A-Za-z0-9\\s])";
        }
        regex += ").*";
        return pwdRequirements.matches(regex);
      } else {
        return true;
      }
    } else {
      return false;
    }
  }

  /**
   * 人员信息 - 编辑
   *
   * @param param 接收一个实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modify(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      Object id = param.get("id");
      Object name = param.get("name");
      if (null != id && !"".equals(id.toString().trim())
              && null != name && !"".equals(name.toString().trim())) {//工号和姓名不能为空
        //工号不能重复
        Condition condition = new ConditionBuilder(User.class).and().equal("id", id).endAnd().buildDone();
        List<User> users = userService.select(condition);
        if (users.isEmpty()) {
          param.put("id", id.toString().trim());
          param.put("name", name.toString().trim());
          User user = new User();
          User modify = user.fromMap(param).buildModifyInfo();
          if (modify.updateById() != -1) {
            return success(Message.UPDATE_SUCCESS);
          } else {
            return fail(Message.UPDATE_FAIL);
          }
        } else {
          return fail(Message.DATA_CANNOT_BE_DUPLICATED);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_EMPTY);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 个人信息维护 - 修改密码
   *
   * @param param 接收rowId、password参数
   * @return PlatResult
   */
  @PostMapping("/modifyPassword")
  public PlatResult modifyPassword(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      //查询原密码
      Condition condition = new ConditionBuilder(User.class).and().equal("rowId", param.get("rowId")).endAnd().buildDone();
      List oldOne = userService.singleSelect(User.class, condition);
      String oldPwd = ((Map) oldOne.get(0)).get("password").toString();//数据库中的原密码
      String oldPassword = String.valueOf(param.get("oldPassword"));//用户输入的原密码
      String password = String.valueOf(param.get("password"));
      if (oldPwd.equals(oldPassword) && validPassword(password)) {//校验密码
        param.remove("oldPassword");
        param.put("passwordUpdateTime", UtilsTool.getDateTimeNow());
        User user = new User().buildModifyInfo().fromMap(param);
        if (user.updateById() != -1) {
          return success(Message.UPDATE_SUCCESS);
        } else {
          return fail(Message.UPDATE_FAIL);
        }
      } else {
        return fail(Message.UPDATE_FAIL);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 删除
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      User user = new User().buildDeleteInfo();
      if (user.logicalDeleteById(rowId) != -1) {
        return success(Message.DELETE_SUCCESS);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 批量删除
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping(value = "/deleteBatch")
  public PlatResult deleteBatch(@RequestParam List<String> rowId) {
    if (UtilsTool.isValid(rowId)) {
      User user = new User().buildDeleteInfo();
      Condition condition = new ConditionBuilder(User.class).and().in("rowId", rowId).endAnd().buildDone();
      if (userService.update(user, condition) != -1) {
        return success(Message.DELETE_SUCCESS);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }


  /**
   * 人员信息 - 锁定账号
   *
   * @param rowId 唯一标识
   *              状态（01：锁定，02：解锁，03：启用，04：失效）
   * @return PlatResult
   */
  @PostMapping("/lock")
  public PlatResult lock(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      return updateBatch(Collections.singletonList(rowId), BaseConstants.LOCKED);
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 批量锁定账号
   *
   * @param rowId 唯一标识
   *              状态（01：锁定，02：解锁，03：启用，04：失效）
   * @return PlatResult
   */
  @PostMapping("/lockBatch")
  public PlatResult lockBatch(@RequestParam List<String> rowId) {
    return updateBatch(rowId, BaseConstants.LOCKED);
  }


  /**
   * 人员信息 - 解锁账号
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/unLock")
  public PlatResult unLock(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      return updateBatch(Collections.singletonList(rowId), BaseConstants.UNLOCK);
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 批量解锁账号
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/unLockBatch")
  public PlatResult unLockBatch(@RequestParam List<String> rowId) {
    return updateBatch(rowId, BaseConstants.UNLOCK);
  }

  /**
   * 人员信息 - 启用账号
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/inUse")
  public PlatResult inUse(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      return updateBatch(Collections.singletonList(rowId), BaseConstants.IN_USE);
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 批量启用账号
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/inUseBatch")
  public PlatResult inUseBatch(@RequestParam List<String> rowId) {
    return updateBatch(rowId, BaseConstants.IN_USE);
  }

  /**
   * 人员信息 - 失效账号
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/outOfUse")
  public PlatResult outOfUse(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      return updateBatch(Collections.singletonList(rowId), BaseConstants.OUT_OF_USE);
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 批量失效账号
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/outOfUseBatch")
  public PlatResult outOfUseBatch(@RequestParam List<String> rowId) {
    return updateBatch(rowId, BaseConstants.OUT_OF_USE);
  }

  /**
   * 人员信息 - 重置密码
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/resetPassword")
  public PlatResult resetPassword(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      return updateBatch(Collections.singletonList(rowId), "resetPassword");
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 批量重置密码
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/resetPasswordBatch")
  public PlatResult resetPasswordBatch(@RequestParam List<String> rowId) {
    return updateBatch(rowId, "resetPassword");
  }

  /**
   * 更改状态时进行批量操作
   *
   * @param rowId  唯一标识数组
   * @param status 状态
   * @return PlatResult
   */
  private PlatResult updateBatch(List<String> rowId, String status) {
    if (UtilsTool.isValid(rowId)) {
      Map<String, String> map = new HashMap<>();
      switch (status) {
        case BaseConstants.LOCKED:
          map.put("status", BaseConstants.LOCKED);
          map.put("accountLockedTime", UtilsTool.getDateTimeNow());
          break;
        case BaseConstants.UNLOCK:
          map.put("status", BaseConstants.UNLOCK);
          map.put("accountLockedTime", "");
          break;
        case BaseConstants.IN_USE:
          map.put("status", BaseConstants.IN_USE);
          break;
        case BaseConstants.OUT_OF_USE:
          map.put("status", BaseConstants.OUT_OF_USE);
          break;
        case "resetPassword":
          map.put("password", SystemSettingManager.getDefaultPwd());
          break;
        default:
      }
      User user = new User().fromMap(map).buildModifyInfo();
      Condition condition = new ConditionBuilder(User.class).and().in("rowId", rowId).endAnd().buildDone();
      if (userService.update(user, condition) != -1) {
        return success(Message.OPERATOR_SUCCESS);
      } else {
        return fail(Message.OPERATOR_FAIL);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 定期更换密码
   *
   * @param userName 用户姓名
   * @return PlatResult
   */
  @GetMapping("/pwdRegularReplace")
  public PlatResult pwdRegularReplace(String userName) {
    Condition condition = new ConditionBuilder(User.class).and().equal("name", userName).endAnd().buildDone();
    List<User> user = userService.select(condition);
    String passwordUpdateTime;
    if (user != null && user.size() != 0) {
      passwordUpdateTime = user.get(0).getPasswordUpdateTime();
      long dateCompare = UtilsTool.dateCompare(passwordUpdateTime, UtilsTool.getDateTimeNow());
      if (dateCompare >= 90) {
        return fail(Message.REGULAR_REPLACE);
      }
      return success(Message.QUERY_SUCCESS);
    }
    return null;
  }

  /**
   * 下载文件请求
   *
   * @param fileName 下载的文件名
   * @param rowIds   下载内容的 rowIds
   * @param fields   下载内容的列
   * @param response 返回的数据内容
   */
  @GetMapping(value = "/downloadExcel")
  @SuppressWarnings("unchecked")
  public void downloadExcel(String fileName, String rowIds, String fields, HttpServletResponse response) {
    String[] _rowIds = null;
    if (null != rowIds) {
      List<String> strings = UtilsTool.jsonToObj(rowIds, List.class, String.class);
      if (null != strings) {
        _rowIds = writeListToArray(strings);
      }
    }

    String[] _fields = null;
    if (null != fields) {
      List<String> strings = UtilsTool.jsonToObj(fields, List.class, String.class);
      if (null != strings) {
        _fields = writeListToArray(strings);
      }
    }

    // 获取 excel
    HSSFWorkbook workbook = userService.exportToExcelByte(_rowIds, _fields);
    response.reset();
    response.setContentType("application/x-msdownload");
    response.setCharacterEncoding("UTF-8");

    if (null == fileName) {
      fileName = "download";
    }
    try {
      response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xls", new String(fileName.getBytes("UTF-8"), "ISO8859-1")));
    } catch (UnsupportedEncodingException e) {
      response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xls", "download"));
      e.printStackTrace();
    }
    try {
      // 将数据写入到输出流
      workbook.write(response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将 List 输出为数组
   *
   * @param list list
   * @return 返回数组
   */
  private String[] writeListToArray(List<String> list) {
    String[] strings = null;
    if (null != list && !list.isEmpty()) {
      strings = new String[list.size()];
      for (int i = 0; i < list.size(); i++) {
        strings[i] = list.get(i);
      }
    }
    return strings;
  }

}
