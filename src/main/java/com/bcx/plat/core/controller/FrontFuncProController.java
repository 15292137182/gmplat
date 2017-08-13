package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping("/core/fronFuncPro")
public class FrontFuncProController extends
    BaseControllerTemplate<FrontFuncProService, FrontFuncPro> {



  @Autowired
  private BusinessObjectProService businessObjectProService;


  public void setBusinessObjectProService(BusinessObjectProService businessObjectProService) {
    this.businessObjectProService = businessObjectProService;
  }

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("rowId");
  }

  /**
   * 查询
   */
  @RequestMapping("/queryProRowId")
  public Object singleQuery(String queryProRowId,HttpServletRequest request, Locale locale) {
    final FrontFuncProService entityService = getEntityService();
    List<Map<String, Object>> result = entityService
            .select(new And(new FieldCondition("rowId", Operator.EQUAL, queryProRowId)));
    result=queryResultProcess(result);
    return result(request,new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result),locale);
  }



  /**
   * 查询
   */
  @RequestMapping("/queryPro")
  public Object singleQuery(String str,String fronByProRowId ,HttpServletRequest request, Locale locale) {
    final FrontFuncProService entityService = getEntityService();
    List<Map<String, Object>> result = entityService
            .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, fronByProRowId),
                    entityService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
    result=queryResultProcess(result);
    return result(request,new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result),locale);
  }



  /**
   * TODO 这个方法后面会有大用处
   * 暂时先放这里 以后再重构
   * @param result
   * @return
   */
  @Override
  protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
    List<String> rowIds = result.stream().map((row) -> {
      return (String) row.get("relateBusiPro");
    }).collect(Collectors.toList());
    List<Map<String, Object>> results = businessObjectProService
            .select(new FieldCondition("rowId", Operator.IN, rowIds)
                    , new Field("row_id", "rowId")
                    , new Field("property_name", "propertyName"));
    HashMap<String,Object> map=new HashMap<>();
    for (Map<String, Object> row : results) {
      map.put((String) row.get("rowId"),row.get("propertyName"));
    }
    for (Map<String, Object> row : result) {
      row.put("propertyName",map.get(row.get("relateBusiPro")));
    }
    return result;
  }
}
