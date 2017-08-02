package com.bcx.plat.core.controller;

<<<<<<< f527fe4ae6c4051abecf375f2312e3ea8964fe72
import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

=======
>>>>>>> 1cf730ea6bd4c7300b31759ea1b7498723282afa
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.service.DBTableColumnService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< f527fe4ae6c4051abecf375f2312e3ea8964fe72
=======
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

>>>>>>> 1cf730ea6bd4c7300b31759ea1b7498723282afa
/**
 * Create By HCL at 2017/8/1
 */
@RestController
@RequestMapping("/plat/dbTableColumn")
<<<<<<< f527fe4ae6c4051abecf375f2312e3ea8964fe72
public class DBTableColumnController extends BaseController {

  @Autowired
  private DBTableColumnService dbTableColumnService;
=======
public class DBTableColumnController extends BaseController{
>>>>>>> 1cf730ea6bd4c7300b31759ea1b7498723282afa

  /**
   * 数据库字段信息查询接口
   *
   * @param str 空格条件
   * @param rowId 主键编号
   * @param request 请求
   * @return 返回查询信息
   */
  @RequestMapping("/select")
  public MappingJacksonValue select(String str, String rowId, HttpServletRequest request,
      Locale locale) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("strArr", collectToSet(str));
    cond.put("rowId", rowId);
    MappingJacksonValue value = new MappingJacksonValue(
        dbTableColumnService.select(cond).convertMsg(locale));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   * 新建数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/insert")
  public MappingJacksonValue insert(DBTableColumn dbTableColumn, HttpServletRequest request,
      Locale locale) {
    MappingJacksonValue value = new MappingJacksonValue(
        dbTableColumnService.insert(dbTableColumn).convertMsg(locale));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   * 更新数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/update")
  public MappingJacksonValue update(DBTableColumn dbTableColumn, HttpServletRequest request,
      Locale locale) {
    MappingJacksonValue value = new MappingJacksonValue(
        dbTableColumnService.update(dbTableColumn).convertMsg(locale));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   * 删除数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/delete")
  public MappingJacksonValue delete(DBTableColumn dbTableColumn, HttpServletRequest request,
      Locale locale) {
    MappingJacksonValue value = new MappingJacksonValue(
        dbTableColumnService.delete(dbTableColumn).convertMsg(locale));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

}