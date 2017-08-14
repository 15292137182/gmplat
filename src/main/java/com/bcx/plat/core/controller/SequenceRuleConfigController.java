package com.bcx.plat.core.controller;


import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By HCL at 2017/8/8
 */
@RestController
@RequestMapping("/core/sequenceRule")
public class SequenceRuleConfigController extends
    BaseControllerTemplate<SequenceRuleConfigService, SequenceRuleConfig> {

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
    ServiceResult _sr = new ServiceResult();
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
}
