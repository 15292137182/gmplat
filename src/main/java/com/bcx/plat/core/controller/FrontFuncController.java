package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端功能模块
 * Created by Went on 2017/8/2.
 */
@RequestMapping("/fronc")
@RestController
public class FrontFuncController {

  @Autowired
  private FrontFuncService frontFuncService;


  /**
   * 查询前端功能模块
   * @param str
   * @param rowId
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/select")
  public MappingJacksonValue select(String str,String rowId, HttpServletRequest request, Locale locale) {
    Map<String, Object> map = new HashMap<>();
    map.put("strArr", collectToSet(str));
    map.put("rowId",rowId);
    ServiceResult<FrontFunc> result = frontFuncService.select(map);
    MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   * 新增前端功能模块
   * @param frontFunc
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/insert")
  public MappingJacksonValue insert(FrontFunc frontFunc, HttpServletRequest request, Locale locale) {
    ServiceResult<FrontFunc> result = frontFuncService.insert(frontFunc);
    MappingJacksonValue mapjack = new MappingJacksonValue(result.convertMsg(locale));
    mapjack.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return mapjack;
  }

  /**
   * 更新前端功能模块
   * @param frontFunc
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/update")
  public MappingJacksonValue update(FrontFunc frontFunc, HttpServletRequest request, Locale locale) {
    ServiceResult<FrontFunc> result = frontFuncService.update(frontFunc);
    MappingJacksonValue mapjack = new MappingJacksonValue(result.convertMsg(locale));
    mapjack.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return mapjack;
  }

  /**
   * 删除前端功能模块
   * @param frontFunc
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/delete")
  public MappingJacksonValue delete(FrontFunc frontFunc, HttpServletRequest request, Locale locale) {
    ServiceResult<FrontFunc> result = frontFuncService.delete(frontFunc);
    MappingJacksonValue mapjack = new MappingJacksonValue(result.convertMsg(locale));
    mapjack.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return mapjack;
  }


}
