package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 前端功能模块 Created by Went on 2017/8/2.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/fronc")
@RestController
public class FrontFuncController extends BaseController {
  private final FrontFuncService frontFuncService;
  private final FrontFuncProService frontFuncProService;
  private final BusinessObjectService businessObjectService;

  @Autowired
  public FrontFuncController(FrontFuncService frontFuncService, FrontFuncProService frontFuncProService, BusinessObjectService businessObjectService) {
    this.frontFuncService = frontFuncService;
    this.frontFuncProService = frontFuncProService;
    this.businessObjectService = businessObjectService;
  }

  /**
   * 需要模糊的搜索字段
   *
   * @return 表字段
   */
  protected List<String> blankSelectFields() {
    return Arrays.asList("funcCode", "funcName");
  }


  /**
   * 查询前端功能块数据
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否根据指定字段查询
      Map map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(FrontFunc.class, map);
    } else {
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = frontFuncService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult(frontFuncService.selectMap(condition, orders));
    }
    List<Map<String, Object>> list = queryResultProcessAction(result.getResult());
    result.setResult(list);
    return result(new ServerResult<>(result));
  }

  /**
   * queryResultProcessAction
   *
   * @param result 接受serviceResult封装的参数
   * @return list
   */
  protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
    List<String> rowIds = result.stream().map((row) ->
        (String) row.get("relateBusiObj")).collect(Collectors.toList());

    List<BusinessObject> results = businessObjectService
        .select(new FieldCondition("rowId", Operator.IN, rowIds));
    HashMap<String, Object> map = new HashMap<>();
    for (BusinessObject row : results) {
      map.put(row.getRowId(), row.getObjectName());
    }
    for (Map<String, Object> row : result) {
      row.put("objectName", map.get(row.get("relateBusiObj")));
    }
    return result;
  }

  /**
   * 判断当前前端功能块下是否有功能块对应的属性数据,有就全部删除
   *
   * @param rowId 功能块rowId
   */
  @RequestMapping("/delete")
  public PlatResult delete(String rowId) {
    ServerResult result = new ServerResult();
    //根据传入的rowId查询当前数据
    Condition condition = new ConditionBuilder(FrontFunc.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<FrontFunc> frontFuncs = frontFuncService.select(condition);

    if (UtilsTool.isValid(rowId)) {
      List<FrontFuncPro> funcRowId = frontFuncProService
          .select(new FieldCondition("funcRowId", Operator.EQUAL, rowId));

      if (UtilsTool.isValid(funcRowId)) {
        List<String> rowIds = funcRowId.stream().map((row) -> row.getRowId()).collect(Collectors.toList());
        List<FrontFuncPro> frontFuncPros = frontFuncProService.select(new FieldCondition("rowId", Operator.IN, rowIds));
        if (frontFuncPros.size() > 0) {
          for (FrontFuncPro front : frontFuncPros) {

          }
          new FrontFuncPro().buildDeleteInfo().deleteById();
        }
      }
      FrontFunc frontFunc = new FrontFunc();
      int del = frontFunc.buildDeleteInfo().deleteById(rowId);
      if (del != -1) {
        return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS, frontFuncs));
      } else {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
      }
    } else {
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }


  /**
   * 根据前端功能块的代码查询出对应的数据
   *
   * @param funcCode ["obj102017-08-31000001"]
   * @return PlatResult
   */
  @RequestMapping("/queryFuncCode")
  public PlatResult queryFuncCode(String funcCode) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(funcCode)) {
      List list = UtilsTool.jsonToObj(funcCode, List.class);
      ServerResult<LinkedList<Map<String, Object>>> linkedListServerResult = frontFuncService.queryFuncCode(list);
      return result(linkedListServerResult);
    }
    return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
  }


  /**
   * 前端功能块新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping("/add")
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    FrontFunc frontFunc = new FrontFunc().buildCreateInfo().fromMap(param);
    int insert = frontFunc.insert();
    if (insert != -1) {
      return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, frontFunc));
    } else {
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL));
    }
  }

  /**
   * 前端功能块根据rowId修改数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping("/modify")
  public PlatResult update(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    int update;
    if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
      FrontFunc frontFunc = new FrontFunc();
      FrontFunc modify = frontFunc.fromMap(param).buildModifyInfo();
      update = modify.updateById();
      if (update != -1) {
        return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, modify));
      } else {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
      }
    }
    return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }

  /**
   * 根据rowId查询数据
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    ServerResult serverResult = new ServerResult();
    if (!rowId.isEmpty()) {
      Condition condition = new ConditionBuilder(FrontFunc.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<FrontFunc> select = frontFuncService.select(condition);
      return result(new ServerResult<>(select));
    } else {
      return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }

}
