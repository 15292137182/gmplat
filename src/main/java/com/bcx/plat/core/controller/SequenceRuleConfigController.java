package com.bcx.plat.core.controller;


import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.SequenceGenerateService;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;

/**
 * Create By HCL at 2017/8/8
 */
@RestController
@RequestMapping("/core/sequenceRule")
public class SequenceRuleConfigController extends
        BaseControllerTemplate<SequenceRuleConfigService, SequenceRuleConfig> {

  private final SequenceGenerateService sequenceGenerateService;

  @Autowired
  public SequenceRuleConfigController(SequenceGenerateService sequenceGenerateService) {
    this.sequenceGenerateService = sequenceGenerateService;
  }

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("seqCode", "seqName", "seqContent", "desp");
  }

  /**
   * 模拟流水号生成接口
   *
   * @param request 请求
   * @return 返回流水号的生成列表
   */
    @RequestMapping("/mock")
  public Object mockSequenceNo(HttpServletRequest request, Locale locale) {
    String _content = request.getParameter("content");
    ServiceResult<List<String>> _sr = new ServiceResult<>();
    if (isValid(_content)) {
      _sr.setState(STATUS_SUCCESS);
      String _args = request.getParameter("args");
      Map<String, Object> map = new HashMap<>();
      try {
        map = jsonToObj(_args, Map.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      String _num = request.getParameter("num") == null ? "" : request.getParameter("num");
      int num = _num.matches("^\\d+$") ? Integer.parseInt(_num) : 5;
      List<String> strings = SequenceManager.getInstance().mockSequenceNo(_content, map, num);
      _sr.setData(strings);
      _sr.setMessage("OPERATOR_SUCCESS");
    } else {
      _sr.setState(STATUS_FAIL);
      _sr.setMessage("INVALID_REQUEST");
    }
    return super.result(request, _sr, locale);
  }

  /**
   * 重置序列号，接受的参数名称为:seqCode,值为序列号的代码值
   *
   * @param request 请求
   * @param locale  国际化信息
   * @return 返回
   */
  @RequestMapping("/reset")
  public Object resetSequenceNo(HttpServletRequest request, Locale locale) {
    String rowId = request.getParameter("rowId");
    String aimValue = request.getParameter("currentValue");
    String serialId = request.getParameter("serialId");
    ServiceResult<List<String>> _sr = new ServiceResult<>();
    if (isValid(rowId)) {
      SequenceManager.getInstance().resetSequenceNo(rowId, serialId, Integer.parseInt(aimValue));
      _sr.setMessage("OPERATOR_SUCCESS");
    } else {
      _sr.setState(STATUS_FAIL);
      _sr.setMessage("INVALID_REQUEST");
    }
    return super.result(request, _sr, locale);
  }

  /**
   * 通用查询方法
   *
   * @param rowId   按照空格查询
   * @param request request请求
   * @param locale  国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryById")
  public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
    Map<String, Object> map = new HashMap<>();
    List<Map<String, Object>> mapList = getEntityService().select(new FieldCondition("rowId", Operator.EQUAL, rowId));
    List<Map<String, Object>> seqRowId = sequenceGenerateService.select(new FieldCondition("seqRowId", Operator.EQUAL, rowId));
    String currentValue = null;
    for (Map<String, Object> seq : seqRowId) {
      currentValue = (String) seq.get("currentValue");
    }
    for (Map<String, Object> maplists : mapList) {
      maplists.put("currenValue", currentValue);
    }
    if (mapList.size() == 0) {
      return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
    }
    return super.result(request, new ServiceResult(mapList, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
  }
}
