package com.bcx.plat.core.controller;

/**
 * Create By HCL at 2017/8/1
 */

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.service.DBTableColumnService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/dbTableColumn")
public class DBTableColumnController extends BaseController {

  @Autowired
  private DBTableColumnService dbTableColumnService;

  /**
   * 数据库字段信息查询接口
   *
   * @param str 空格条件
   * @param rowId 主键编号
   * @param request 请求
   * @return 返回查询信息
   */
  @RequestMapping("/query")
  public Object select(String str, String rowId, HttpServletRequest request,
      Locale locale) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("strArr", collectToSet(str));
    cond.put("rowId", rowId);
    return super.result(request, dbTableColumnService.select(cond), locale);
  }

  /**
   * 查询数据库表中关联数据中字段信息
   *
   * @param rowId 查询条件
   * @return 返回查询结果
   */
  @RequestMapping("/queryByTableId")
  public Object selectByTableId(String rowId, HttpServletRequest request,
      Locale locale) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("rowId", rowId);
    return super.result(request, dbTableColumnService.select(cond), locale);
  }

  /**
   * 新建数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/add")
  public Object insert(DBTableColumn dbTableColumn, HttpServletRequest request,
      Locale locale) {
    return super.result(request, dbTableColumnService.insert(dbTableColumn), locale);
  }

  /**
   * 更新数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/modify")
  public Object update(DBTableColumn dbTableColumn, HttpServletRequest request,
      Locale locale) {
    return super.result(request, dbTableColumnService.update(dbTableColumn), locale);
  }

  /**
   * 删除数据
   *
   * @return 返回
   */
  @RequestMapping("/delete")
  public Object delete(HttpServletRequest request,
      Locale locale) {
    Map map = new HashMap();
    map.put("rowIds", collectToSet(request.getParameter("rowIds")));
    return super.result(request, dbTableColumnService.batchDelete(map), locale);
  }

}