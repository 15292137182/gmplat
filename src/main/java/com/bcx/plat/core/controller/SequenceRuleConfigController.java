package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SequenceGenerate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.manager.TXManager;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.SequenceGenerateService;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;

@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/sequenceRule")
public class SequenceRuleConfigController extends BaseController {
  @Autowired
  private SequenceRuleConfigService sequenceRuleConfigService;
  @Autowired
  private SequenceGenerateService sequenceGenerateService;

  /**
   * @return 参与空格查询的字段
   */
  private List<String> blankSelectFields() {
    return Arrays.asList("seqCode", "seqCode", "seqName", "seqContent", "desp");
  }

  /**
   * 通用查询方法
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult selectWithPage(String search,
                                   @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    Or blankQuery = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    PageResult<Map<String, Object>> result;
    if (UtilsTool.isValid(pageSize)) { // 如果有分页参数进行分页查询
      result = sequenceRuleConfigService.selectPageMap(blankQuery, orders, pageNum, pageSize);
    } else { // 如果没有分页参数，查询全部
      result = new PageResult(sequenceRuleConfigService.selectMap(blankQuery, orders));
    }
    return result(new ServerResult<>(result));
  }

  /**
   * 序列规则新增方法
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping("/add")
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    SequenceRuleConfig sequenceRuleConfig = new SequenceRuleConfig().buildCreateInfo().fromMap(param);
    int insert = sequenceRuleConfig.insert();
    if (insert != -1) {
      return super.result(new ServerResult().setStateMessage(STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
    } else {
      return super.result(new ServerResult().setStateMessage(STATUS_FAIL, Message.NEW_ADD_FAIL));
    }
  }


  /**
   * 通过修改方法
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping("/modify")
  public PlatResult update(@RequestParam Map<String, Object> param) {
    int update;
    if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
      SequenceRuleConfig sequenceRuleConfig = new SequenceRuleConfig();
      SequenceRuleConfig modify = sequenceRuleConfig.fromMap(param).buildModifyInfo();
      update = modify.updateById();
      if (update != -1) {
        return super.result(new ServerResult().setStateMessage(STATUS_SUCCESS, Message.UPDATE_SUCCESS));
      } else {
        return super.result(new ServerResult().setStateMessage(STATUS_FAIL, Message.UPDATE_FAIL));
      }
    }
    return super.result(new ServerResult().setStateMessage(STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }

  /**
   * 通用删除方法
   *
   * @param rowId 按照rowId查询
   * @return 返回操作信息
   */
  @RequestMapping("/delete")
  public PlatResult delete(String rowId) {
    int del;
    if (!rowId.isEmpty()) {
      SequenceRuleConfig sequenceRuleConfig = new SequenceRuleConfig();
      del = sequenceRuleConfig.deleteById(rowId);
      if (del != -1) {
        return result(new ServerResult().setStateMessage(STATUS_FAIL, Message.DELETE_SUCCESS));
      } else {
        return result(new ServerResult().setStateMessage(STATUS_FAIL, Message.DELETE_SUCCESS));
      }
    } else {
      return result(new ServerResult().setStateMessage(STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }

  /**
   * 模拟流水号生成接口
   *
   * @param request 请求
   * @return 返回流水号的生成列表
   */
  @RequestMapping("/mock")
  @SuppressWarnings("unchecked")
  public PlatResult mockSequenceNo(HttpServletRequest request) {
    String _content = request.getParameter("content");
    ServerResult<List<String>> _sr = new ServerResult<>();
    if (isValid(_content)) {
      _sr.setState(STATUS_SUCCESS);
      String _args = request.getParameter("args");
      String _num = request.getParameter("num") == null ? "" : request.getParameter("num");
      int num = _num.matches("^\\d+$") ? Integer.parseInt(_num) : 5;
      List<String> strings = SequenceManager.getInstance().mockSequenceNo(_content, jsonToObj(_args, Map.class), num);
      _sr.setData(strings);
      _sr.setMsg("OPERATOR_SUCCESS");
    } else {
      _sr.setState(STATUS_FAIL);
      _sr.setMsg("INVALID_REQUEST");
    }
    return super.result(_sr);
  }

  /**
   * 重置序列号，接受的参数名称为:seqCode,值为序列号的代码值
   *
   * @param request 请求
   * @return 返回
   */
  @RequestMapping(value = "/reset")
  public Object resetSequenceNo(HttpServletRequest request) {
    String rowId = request.getParameter("rowId");
    ServerResult<List<String>> _sr = new ServerResult<>();
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
    return super.result(_sr);
  }

  /**
   * 通用查询方法
   *
   * @param rowId 按照空格查询
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  @SuppressWarnings("unchecked")
  public Object queryById(String rowId) {
    if (isValid(rowId)) {
      List<Map> mapLists = sequenceRuleConfigService.selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
      List<SequenceGenerate> generates = sequenceGenerateService.select(new FieldCondition("seqRowId", Operator.EQUAL, rowId));
      if (generates.size() > 0) {
        SequenceGenerate generate = generates.get(0);
        for (Map<String, Object> mapList : mapLists) {
          mapList.put("currentValue", generate.getCurrentValue());
          mapList.put("variableKey", generate.getVariableKey());
        }
        if (mapLists.size() == 0) {
          return super.result(new ServerResult().setStateMessage(STATUS_FAIL, Message.QUERY_FAIL));
        } else {
          return super.result(new ServerResult(STATUS_SUCCESS, Message.QUERY_SUCCESS, mapLists));
        }
      } else {
        return super.result(new ServerResult(STATUS_SUCCESS, Message.QUERY_SUCCESS, mapLists));
      }
    } else {
      return super.result(new ServerResult().setStateMessage(STATUS_FAIL, Message.QUERY_FAIL));
    }

  }
}
