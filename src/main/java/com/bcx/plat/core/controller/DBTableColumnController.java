package com.bcx.plat.core.controller;

/**
 * Create By HCL at 2017/8/1
 */

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/core/dbTableColumn")
public class DBTableColumnController extends BaseControllerTemplate<DBTableColumnService,DBTableColumn> {
  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("columnEname","columnCname");
  }

  @Autowired
  DBTableColumnService dbTableColumnService;

  public void setDbTableColumnService(DBTableColumnService dbTableColumnService) {
    this.dbTableColumnService = dbTableColumnService;
  }

  /**
   * 通用查询方法
   *
   * @param args     按照空格查询
   * @param request request请求
   * @param locale  国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryTabById")
  public Object singleInputSelect(String args, String rowId, HttpServletRequest request, Locale locale) {
    List<Map<String, Object>> result = dbTableColumnService.select(new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId),
            UtilsTool.createBlankQuery(Arrays.asList("columnEname", "columnCname"), UtilsTool.collectToSet(args))));
    if (result.size()==0) {
      return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL),locale);
    }
    return super.result(request, new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result), locale);
  }

}