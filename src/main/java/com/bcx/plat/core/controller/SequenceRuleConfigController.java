//package com.bcx.plat.core.controller;
//
//
//import com.bcx.plat.core.base.BaseConstants;
//import com.bcx.plat.core.base.BaseController;
//import com.bcx.plat.core.constants.Message;
//import com.bcx.plat.core.entity.SequenceGenerate;
//import com.bcx.plat.core.entity.SequenceRuleConfig;
//import com.bcx.plat.core.manager.SequenceManager;
//import com.bcx.plat.core.manager.TXManager;
//import com.bcx.plat.core.morebatis.component.FieldCondition;
//import com.bcx.plat.core.morebatis.component.Order;
//import com.bcx.plat.core.morebatis.component.constant.Operator;
//import com.bcx.plat.core.service.SequenceGenerateService;
//import com.bcx.plat.core.service.SequenceRuleConfigService;
//import com.bcx.plat.core.utils.SystemResult;
//import com.bcx.plat.core.utils.ServerResult;
//import com.bcx.plat.core.utils.UtilsTool;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
//import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
//import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
//import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
//import static com.bcx.plat.core.utils.UtilsTool.*;
//
///**
// * 序列号管理类
// *
// * Create By HCL at 2017/8/8
// */
//@RestController
//@RequestMapping(PLAT_SYS_PREFIX + "/core/sequenceRule")
//public class SequenceRuleConfigController extends BaseController<SequenceRuleConfigService> {
//
//  private final SequenceGenerateService sequenceGenerateService;
//
//  /**
//   * 自动装配 Service
//   *
//   * @param sequenceGenerateService 装入 Service
//   */
//  @Autowired
//  public SequenceRuleConfigController(SequenceGenerateService sequenceGenerateService) {
//    this.sequenceGenerateService = sequenceGenerateService;
//  }
//
//  @Override
//  protected List<String> blankSelectFields() {
//    return Arrays.asList("seqCode", "seqName", "seqContent", "desp");
//  }
//
//  /**
//   * 通用查询方法
//   *
//   * @param search   按照空格查询
//   * @param pageNum  当前第几页
//   * @param pageSize 一页显示多少条
//   * @param locale   国际化参数
//   * @return SystemResult
//   */
//  @RequestMapping("/queryPage")
//  public Object singleInputSelect(String search,
//                                  @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
//                                  @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
//                                  String order,
//                                  Locale locale, HttpServletRequest request) {
//    LinkedList<Order> orders = dataSort(order);
//    pageNum = search == null || search.isEmpty() ? 1 : pageNum;
//    return selectPage(locale, createBlankQuery(blankSelectFields(), collectToSet(search)), orders, pageNum, pageSize);
//  }
//
//  /**
//   * 通用新增方法
//   *
//   * @param entity  接受一个实体参数
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping("/add")
//  public Object insert(Map entity, HttpServletRequest request, Locale locale) {
//    return super.insert(new SequenceRuleConfig().fromMap(entity), request, locale);
//  }
//
//
//  /**
//   * 通过修改方法
//   *
//   * @param entity  接受一个实体参数
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping("/modify")
//  public Object update(Map entity, HttpServletRequest request, Locale locale) {
//    return super.updateById(new SequenceRuleConfig().fromMap(entity), request, locale);
//  }
//
//  /**
//   * 通用删除方法
//   *
//   * @param rowId   按照rowId查询
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping("/delete")
//  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
//    return deleteByIds(request, locale, rowId);
//  }
//
//  /**
//   * 模拟流水号生成接口
//   *
//   * @param request 请求
//   * @return 返回流水号的生成列表
//   */
//  @RequestMapping("/mock")
//  @SuppressWarnings("unchecked")
//  public Object mockSequenceNo(HttpServletRequest request, Locale locale) {
//    String _content = request.getParameter("content");
//    ServerResult<List<String>> _sr = new ServerResult<>();
//    if (isValid(_content)) {
//      _sr.setState(STATUS_SUCCESS);
//      String _args = request.getParameter("args");
//      String _num = request.getParameter("num") == null ? "" : request.getParameter("num");
//      int num = _num.matches("^\\d+$") ? Integer.parseInt(_num) : 5;
//      List<String> strings = SequenceManager.getInstance().mockSequenceNo(_content, jsonToObj(_args, Map.class), num);
//      _sr.setData(strings);
//      _sr.setMsg("OPERATOR_SUCCESS");
//    } else {
//      _sr.setState(STATUS_FAIL);
//      _sr.setMsg("INVALID_REQUEST");
//    }
//    return super.result(request, SystemResult.Msg(_sr), locale);
//  }
//
//  /**
//   * 重置序列号，接受的参数名称为:seqCode,值为序列号的代码值
//   *
//   * @param request 请求
//   * @param locale  国际化信息
//   * @return 返回
//   */
//  @RequestMapping(value = "/reset")
//  public Object resetSequenceNo(HttpServletRequest request, Locale locale) {
//    String rowId = request.getParameter("rowId");
//    ServerResult<List<String>> _sr = new ServerResult<>();
//    _sr.setState(STATUS_FAIL);
//    _sr.setMsg("INVALID_REQUEST");
//    if (isValid(rowId)) {
//      String content = request.getParameter("content");
//      List list = jsonToObj(content, ArrayList.class);
//      if (null != list) {
//        boolean success = true;
//        String message = "操作成功！流水号已重设！";
//        try {
//          // 开启事务管理要成功全成功，要失败全失败
//          TXManager.doInNewTX(((manager, status) -> {
//            for (Object obj : list) {
//              Map ele = jsonToObj(objToJson(obj), HashMap.class);
//              if (ele != null) {
//                String key = ele.get("key").toString();
//                String value = ele.get("value").toString();
//                if (isValid(key) && value.matches("\\d+")) {
//                  String[] objectSigns = new String[]{};
//                  List os = jsonToObj(objToJson(ele.get("objectSigns")), List.class);
//                  for (int i = 0; os != null && i < os.size(); i++) {
//                    objectSigns[i] = os.get(i).toString();
//                  }
//                  SequenceManager.getInstance().resetSequenceNo(rowId, key, Integer.parseInt(value), objectSigns);
//                }
//              }
//            }
//          }));
//        } catch (Exception e) {
//          e.printStackTrace();
//          success = false;
//          message = e.getMessage();
//        }
//        _sr.setState(success ? STATUS_SUCCESS : STATUS_FAIL);
//        _sr.setMsg(message);
//      }
//    }
//    return super.result(request, SystemResult.Msg(_sr), locale);
//  }
//
//  /**
//   * 通用查询方法
//   *
//   * @param rowId   按照空格查询
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return SystemResult
//   */
//  @RequestMapping("/queryById")
//  @SuppressWarnings("unchecked")
//  public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
//    if (UtilsTool.isValid(rowId)) {
//      List<Map> mapLists = getService().selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
//      List<SequenceGenerate> generates = sequenceGenerateService.selectEntity(new FieldCondition("seqRowId", Operator.EQUAL, rowId));
//      SequenceGenerate generate = generates.get(0);
//      for (Map<String, Object> mapList : mapLists) {
//        mapList.put("currentValue", generate.getCurrentValue());
//        mapList.put("variableKey", generate.getVariableKey());
//      }
//      if (mapLists.size() == 0) {
//        return super.result(request, SystemResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
//      } else {
//        return super.result(request, SystemResult.Msg(new ServerResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, mapLists)), locale);
//      }
//    } else {
//      return super.result(request, SystemResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
//    }
//
//  }
//}
