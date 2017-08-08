package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.service.BusinessObjectService;
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
 * Created by Went on 2017/8/1.
 */
@RestController
@RequestMapping("/core/businObj")
public class BusinessObjectController {

  @Autowired
  private BusinessObjectService businessObjectService;
  @Autowired
  private BaseService businessObjectServiceA;

  public void setBusinessObjectServiceA(BaseService businessObjectServiceA) {
    this.businessObjectServiceA = businessObjectServiceA;
  }

  /**
   * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  @RequestMapping("/query")
  public MappingJacksonValue select(String str, HttpServletRequest request, Locale locale) {


    Map<String, Object> cond = new HashMap<>();
    cond.put("strArr", collectToSet(str));
    ServiceResult<BusinessObject> result = businessObjectService.select(cond);
    MappingJacksonValue value = new MappingJacksonValue(result);
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   * 新增业务对象:对象代码，对象名称，关联表(单选)，版本(系统生成)
   */
  @RequestMapping("/add")
  public MappingJacksonValue insert(BusinessObject businessObject, HttpServletRequest request, Locale locale) {
    ServiceResult<BusinessObject> result=businessObjectServiceA.insert(businessObject.buildCreateInfo().toMap());
//    ServiceResult<BusinessObject> result = businessObjectService.insert(businessObject);
    MappingJacksonValue value = new MappingJacksonValue(result);
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }


  /**
   * 编辑业务对象名称字段
   */
  @RequestMapping("/modify")
  public MappingJacksonValue update(BusinessObject businessObject, HttpServletRequest request, Locale locale) {
    ServiceResult<BusinessObject> result=businessObjectServiceA.update(businessObject.buildModifyInfo().toMap());
//    ServiceResult<BusinessObject> result = businessObjectService.update(businessObject);
    MappingJacksonValue value = new MappingJacksonValue(result);
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   * 删除业务对象
   */
  @RequestMapping("/delete")
  public MappingJacksonValue delete(String rowId, HttpServletRequest request, Locale locale) {
    Map<String,Object> args=new HashMap<>();
    args.put("rowId",rowId);
    ServiceResult<BusinessObject> result=businessObjectServiceA.delete(args);
//    ServiceResult<BusinessObject> result = businessObjectService.delete(rowId);
    MappingJacksonValue value = new MappingJacksonValue(result);
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  /**
   *  对该条记录失效变为生效
   * @param rowId  接受的唯一标示
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/takeEff")
  public MappingJacksonValue updateTakeEffect(String rowId,HttpServletRequest request,Locale locale){
    ServiceResult result = businessObjectService.updateTakeEffect(rowId);
    MappingJacksonValue value = new MappingJacksonValue(result);
    value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }
}
