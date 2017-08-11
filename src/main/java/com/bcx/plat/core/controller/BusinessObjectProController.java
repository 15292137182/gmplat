package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping("/core/businObjPro")
@RestController
public class BusinessObjectProController extends
    BaseControllerTemplate<BusinessObjectProService, BusinessObjectPro> {

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("propertyCode", "propertyName");
  }

  /**
   * 新增业务对象:对象代码，对象名称，关联表(单选)，版本(系统生成)
   */
  @RequestMapping("/add")
  @Override
  public Object insert(BusinessObjectPro entity, HttpServletRequest request, Locale locale) {
    entity.setPropertyCode("A00" + UtilsTool.lengthUUID(3).toUpperCase());
    return super.insert(entity, request, locale);
  }

  /**
   * 查询业务对象
   */
  @RequestMapping("/query")
  public Object singleInputSelect(String str, String objRowId, HttpServletRequest request,Locale locale) {
    final BusinessObjectProService entityService = getEntityService();
    List<Map<String, Object>> result = entityService
        .select(new And(new FieldCondition("objRowId", Operator.EQUAL, objRowId),
            entityService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
    return result(request,new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result),locale);
  }
}
