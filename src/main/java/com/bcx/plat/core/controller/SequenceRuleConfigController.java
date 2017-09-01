package com.bcx.plat.core.controller;


import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.manager.TXManager;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.SequenceGenerateService;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * Create By HCL at 2017/8/8
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/sequenceRule")
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
    PlatResult<List<String>> _sr = new PlatResult<>();
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
      _sr.setMsg("OPERATOR_SUCCESS");
    } else {
      _sr.setState(STATUS_FAIL);
      _sr.setMsg("INVALID_REQUEST");
    }
    return super.result(request, ServiceResult.Msg(_sr), locale);
  }

  /**
   * 重置序列号，接受的参数名称为:seqCode,值为序列号的代码值
   *
   * @param request 请求
   * @param locale  国际化信息
   * @return 返回
   */
  @RequestMapping(value = "/reset")
  public Object resetSequenceNo(HttpServletRequest request, Locale locale) {
    String rowId = request.getParameter("rowId");
    PlatResult<List<String>> _sr = new PlatResult<>();
    _sr.setState(STATUS_FAIL);
    _sr.setMsg("INVALID_REQUEST");
    if (isValid(rowId)) {
      String content = request.getParameter("content");
      List list = jsonToObj(content, ArrayList.class);
      if (null != list) {
        boolean success = true;
        String message = "操作成功！流水号已重设！";
        try {
          // 开启事务管理要成功全成功，要失败全失败
          TXManager.doInNewTX(((manager, status) -> {
            for (Object obj : list) {
              Map ele = jsonToObj(objToJson(obj), HashMap.class);
              if (ele != null) {
                String key = ele.get("key").toString();
                String value = ele.get("value").toString();
                if (isValid(key) && value.matches("\\d+")) {
                  String[] objectSigns = new String[]{};
                  List os = jsonToObj(objToJson(ele.get("objectSigns")), List.class);
                  for (int i = 0; os != null && i < os.size(); i++) {
                    objectSigns[i] = os.get(i).toString();
                  }
                  SequenceManager.getInstance().resetSequenceNo(rowId, key, Integer.parseInt(value), objectSigns);
                }
              }
            }
          }));
        } catch (Exception e) {
          e.printStackTrace();
          success = false;
          message = e.getMessage();
        }
        _sr.setState(success ? STATUS_SUCCESS : STATUS_FAIL);
        _sr.setMsg(message);
      }
    }
    return super.result(request, ServiceResult.Msg(_sr), locale);
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
    List<Map<String, Object>> mapList = getEntityService().select(new FieldCondition("rowId", Operator.EQUAL, rowId));
    List<Map<String, Object>> seqRowId = sequenceGenerateService.select(new FieldCondition("seqRowId", Operator.EQUAL, rowId));
    String currentValue = null;
    String variableKey = null;
    for (Map<String, Object> seq : seqRowId) {
      currentValue = (String) seq.get("currentValue");
      variableKey = (String) seq.get("variableKey");
    }
    for (Map<String, Object> maplists : mapList) {
      maplists.put("currenValue", currentValue);
      maplists.put("variableKey", variableKey);

    }
    if (mapList.size() == 0) {
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }
    return super.result(request, ServiceResult.Msg(new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, mapList)), locale);
  }
}
