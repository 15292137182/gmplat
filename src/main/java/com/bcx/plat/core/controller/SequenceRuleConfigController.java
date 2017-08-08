package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By HCL at 2017/8/8
 */
@RestController
@RequestMapping("/code/sequenceRule")
public class SequenceRuleConfigController extends BaseController {

  @Autowired
  private SequenceRuleConfigService sequenceRuleConfigService;

  /**
   * 序列号查询接口
   *
   * @param str 空格条件
   * @param request 请求
   * @return 返回查询信息
   */
  @RequestMapping("/query")
  public Object select(String str, HttpServletRequest request,
      Locale locale) {
    Map<String, Object> cond = new HashMap<>();
    cond.putAll(request.getParameterMap());
    cond.put("strArr", collectToSet(str));
    return super.result(request, sequenceRuleConfigService.select(cond), locale);
  }

  /**
   * 新建数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/add")
  public Object insert(SequenceRuleConfig dbTableColumn, HttpServletRequest request,
      Locale locale) {
    return super.result(request, sequenceRuleConfigService.insert(dbTableColumn), locale);
  }

  /**
   * 更新数据
   *
   * @param dbTableColumn javaBean
   * @return 返回
   */
  @RequestMapping("/modify")
  public Object update(SequenceRuleConfig dbTableColumn, HttpServletRequest request,
      Locale locale) {
    return super.result(request, sequenceRuleConfigService.update(dbTableColumn), locale);
  }

  /**
   * 删除数据
   *
   * @return 返回
   */
  @RequestMapping("/delete")
  public Object delete(HttpServletRequest request,
      Locale locale) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("rowIds", collectToSet(request.getParameter("rowIds")));
    return super.result(request, sequenceRuleConfigService.delete(cond), locale);
  }
}
