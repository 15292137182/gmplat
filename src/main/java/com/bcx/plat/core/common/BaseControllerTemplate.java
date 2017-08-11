package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
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

  protected abstract List<String> blankSelectFields();

  protected List<Map<String,Object>> queryResultProcess(List<Map<String,Object>> result){
    return UtilsTool.isValid(result)?queryResultProcessAction(result):result;
  }

  protected PageResult<Map<String,Object>> queryResultProcess(PageResult<Map<String,Object>> result){
    result.setResult(queryResultProcess(result.getResult()));
    return result;
  }

  protected List<Map<String,Object>> queryResultProcessAction(List<Map<String,Object>> result){
    return result;
  }

  public void setEntityService(T entityService) {
    this.entityService = entityService;
  }

  protected T getEntityService(){
    return entityService;
  }

  /**
   * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  @RequestMapping("/query")
  public Object singleInputSelect(String str, HttpServletRequest request, Locale locale) {
    List<Map<String,Object>> result = entityService
        .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
    return super.result(request, commonServiceResult(queryResultProcess(result),Message.QUERY_SUCCESS), locale);
  }

  /**
   * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  @RequestMapping("/queryPage")
  public Object singleInputSelect(String args,int pageNum,int pageSize, HttpServletRequest request, Locale locale) {
    PageResult<Map<String,Object>> result = entityService
        .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(args), pageNum, pageSize);
    return super.result(request, commonServiceResult(queryResultProcess(result),Message.QUERY_SUCCESS), locale);
  }

  @RequestMapping("/select")
  public Object select(Map<String,Object> args, HttpServletRequest request, Locale locale) {
    List<Map<String,Object>> result = entityService.select(args);
    return super.result(request, commonServiceResult(queryResultProcess(result),Message.QUERY_SUCCESS), locale);
  }

  @RequestMapping("/selectPage")
  public Object select(Map<String,Object> args,int pageNum,int pageSize, HttpServletRequest request, Locale locale) {
    PageResult<Map<String,Object>> result = entityService.select(args, pageNum, pageSize);
    return super.result(request, commonServiceResult(queryResultProcess(result),Message.QUERY_SUCCESS), locale);
  }

  /**
   * 新增业务对象
   */
  @RequestMapping("/add")
  public Object insert(Y entity, HttpServletRequest request, Locale locale) {
    entityService.insert(entity.buildCreateInfo().toMap());
    return super.result(request, commonServiceResult(entity,Message.NEW_ADD_SUCCESS), locale);
  }


  /**
   * 编辑业务对象名称字段
   */
  @RequestMapping("/modify")
  public Object update(Y entity, HttpServletRequest request, Locale locale) {
    entityService.update(entity.buildModifyInfo().toMap());
    return super.result(request, commonServiceResult(entity,Message.UPDATE_SUCCESS), locale);
  }

  /**
   * 删除业务对象
   */
  @RequestMapping("/delete")
  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
    Map<String, Object> args = new HashMap<>();
    args.put("rowId", rowId);
    entityService.delete(args);
    return super.result(request, commonServiceResult(rowId,Message.DELETE_SUCCESS), locale);
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
    entityService.update(map);
    return super.result(request, commonServiceResult(rowId, Message.UPDATE_SUCCESS), locale);
  }

  private <T> ServiceResult<T> commonServiceResult(T content,String msg){
    return new ServiceResult<>(BaseConstants.STATUS_SUCCESS,msg,content);
  }
}