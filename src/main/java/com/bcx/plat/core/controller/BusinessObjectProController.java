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
   * 查询业务对象
   */
  @RequestMapping("/querySlave")
  public Object querySlave(String str, String objRowId, HttpServletRequest request,Locale locale) {
    final BusinessObjectProService entityService = getEntityService();
    List<Map<String, Object>> result = entityService
        .select(new And(new FieldCondition("objRowId", Operator.EQUAL, objRowId),
            entityService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
    return result(request,new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result),locale);
  }
}
