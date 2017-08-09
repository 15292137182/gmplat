package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class BaseControllerTemplate<T extends BaseServiceTemplate,Y extends BaseEntity<Y>> extends BaseController {
  @Autowired
  private T entityService;

  public void setEntityService(T entityService) {
    this.entityService = entityService;
  }

  /**
   * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  @RequestMapping("/query")
  public Object singleInputSelect(String str, HttpServletRequest request, Locale locale) {
    ServiceResult<List<Map<String,Object>>> result = entityService
        .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));

    return super.result(request, result, locale);
  }

  /**
   * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  @RequestMapping("/queryPage")
  public Object singleInputSelect(String args,int pageNum,int pageSize, HttpServletRequest request, Locale locale) {
    ServiceResult<List<Map<String,Object>>> result = entityService
        .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(args),pageNum,pageSize);
    return super.result(request, result, locale);
  }

  @RequestMapping("/select")
  public Object select(Map<String,Object> args, HttpServletRequest request, Locale locale) {
    ServiceResult<List<Map<String,Object>>> result = entityService.select(args);
    return super.result(request, result, locale);
  }

  @RequestMapping("/selectPage")
  public Object select(Map<String,Object> args,int pageNum,int pageSize, HttpServletRequest request, Locale locale) {
    ServiceResult<List<Map<String,Object>>> result = entityService.select(args,pageNum,pageSize);
    return super.result(request, result, locale);
  }

  protected abstract List<String> blankSelectFields();

  /**
   * 新增业务对象:对象代码，对象名称，关联表(单选)，版本(系统生成)
   */
  @RequestMapping("/add")
  public Object insert(Y entity, HttpServletRequest request, Locale locale) {
    ServiceResult<Map<String, Object>> result = entityService
        .insert(entity.buildCreateInfo().toMap());
    return super.result(request, result, locale);
  }


  /**
   * 编辑业务对象名称字段
   */
  @RequestMapping("/modify")
  public Object update(Y entity, HttpServletRequest request, Locale locale) {
    ServiceResult<Map<String, Object>> result = entityService
        .update(entity.buildModifyInfo().toMap());
    return super.result(request, result, locale);
  }

  /**
   * 删除业务对象
   */
  @RequestMapping("/delete")
  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
    Map<String, Object> args = new HashMap<>();
    args.put("rowId", rowId);
    ServiceResult<Object> result = entityService.delete(args);
    return super.result(request, result, locale);
  }

  /**
   * 对该条记录失效变为生效
   *
   * @param rowId   接受的唯一标示
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/takeEffect")
  public Object updateTakeEffect(String rowId, HttpServletRequest request, Locale locale) {
    HashMap<String,Object> map=new HashMap<>();
    map.put("rowId",rowId);
    map.put("status",BaseConstants.TAKE_EFFECT);
    ServiceResult result = entityService.update(map);
    return super.result(request, result, locale);
  }
}