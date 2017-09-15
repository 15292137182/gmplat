package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.service.TemplateObjectService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * Title: TemplateObjectController</p>
 * Description: 模板对象控制层
 * Copyright: Shanghai BatchSight GMP Information of management platform, Inc. Copyright(c) 2017
 *
 * @author Wen TieHu
 * <pre>History:
 *                                         2017/8/28  Wen TieHu Create
 *                                         </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/templateObj")
public class TemplateObjectController extends BaseController {

  @Autowired
  private TemplateObjectProService templateObjectProService;
  @Autowired
  private TemplateObjectService templateObjectService;

  /**
   * @return 参与空格查询的字段
   */
  public List<String> blankSelectFields() {
    return Arrays.asList("templateCode", "templateCode", "templateName");
  }

  /**
   * 分页方式查询对象属性
   *
   * @param rowId    这个rowId指模版对象的RowId
   * @param search   空格查询的字符串
   * @param pageNum  页面号
   * @param pageSize 页面大小
   * @param order    排序信息（字符串）
   * @return 返回查询结果
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryPropertiesPage(String rowId,
                                        String search,
                                        @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                        String order) {
    ServerResult serverResult = new ServerResult();

    //查询属性的搜索条件
    Or blankQuery = search.isEmpty() ? null : UtilsTool.createBlankQuery(Arrays.asList("code", "cname", "ename", "valueType"), UtilsTool.collectToSet(search));
    Condition condition;
    if (UtilsTool.isValid(search)) {
      condition = new ConditionBuilder(TemplateObjectPro.class)
              .and().equal("templateObjRowId", rowId)
              .or().addCondition(blankQuery).endOr()
              .endAnd().buildDone();
    } else {
      condition = new ConditionBuilder(TemplateObjectPro.class).and().equal("templateObjRowId", rowId).endAnd().buildDone();
    }
    List<Order> orders = dataSort(order);
    PageResult<Map<String, Object>> result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);
    if (result.getResult().size() == 0) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.QUERY_FAIL));
    }
    return result(new ServerResult<>(result));
  }

  /**
   * 查询信息
   *
   * @param search 空格查询的值
   * @return 返回
   */
  @RequestMapping("/query")
  public PlatResult singleInputSelect(String search) {
    List<Map> maps = templateObjectService.selectMap(createBlankQuery(blankSelectFields(), collectToSet(search)));
    return result(new ServerResult<>(maps));
  }

  /**
   * 根据功能块rowId查询当前数据
   *
   * @param rowId 功能块rowId
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    List<Map> maps = templateObjectService.selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
    return result(new ServerResult<>(maps));
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
  public PlatResult singleInputSelect(String search,
                                      @RequestParam(value = "pageNum", required = false) int pageNum,
                                      @RequestParam(value = "pageSize", required = false) int pageSize,
                                      String order) {
    LinkedList<Order> orders = dataSort(order);
    Or blankQuery = search.isEmpty() ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    PageResult<Map<String, Object>> pageResult = templateObjectService.selectPageMap(blankQuery, orders, pageNum, pageSize);
    return result(new ServerResult<>(pageResult));
  }

  /**
   * 通用新增方法
   *
   * @return 返回操作信息
   */
  @RequestMapping("/add")
  public PlatResult insert(@RequestParam Map map) {
    ServerResult serverResult = new ServerResult();
    int status = new TemplateObject().fromMap(map).buildCreateInfo().insert();
    if (-1 == status) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.NEW_ADD_FAIL));
    }
    return result(serverResult.setStateMessage(STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
  }

  /**
   * 通过修改方法
   *
   * @param map 实体类的json字符串
   * @return 返回操作信息
   */
  @RequestMapping("/modify")
  public PlatResult update(@RequestParam Map map) {
    ServerResult serverResult = new ServerResult();
    int status = new TemplateObject().fromMap(map).buildModifyInfo().updateById();
    if (-1 == status) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.UPDATE_FAIL));
    }
    return result(serverResult.setStateMessage(STATUS_SUCCESS, Message.UPDATE_SUCCESS));
  }

  /**
   * 通用删除方法
   *
   * @param rowId 按照rowId查询
   * @return 返回操作信息
   */
  @RequestMapping("/delete")
  public PlatResult delete(String rowId) {
    ServerResult serverResult = new ServerResult();
    int status = new TemplateObject().buildDeleteInfo().deleteById(rowId);
    if (-1 == status) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.DELETE_FAIL));
    }
    return result(serverResult.setStateMessage(STATUS_SUCCESS, Message.DELETE_SUCCESS));
  }
}
