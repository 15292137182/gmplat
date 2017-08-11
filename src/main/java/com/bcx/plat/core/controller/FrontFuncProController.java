package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    return null;
  }

  /**
   * 查询业务对象
   */
  @RequestMapping("/query")
  public Object singleInputSelect(String str, String proRowId, HttpServletRequest request,
      Locale locale) {
    final FrontFuncProService entityService = getEntityService();
    List<Map<String, Object>> result = entityService
        .select(new And(new FieldCondition("relateBusiPro", Operator.EQUAL, proRowId),
            entityService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
    return result(request,
        new ServiceResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result), locale);
  }
}
