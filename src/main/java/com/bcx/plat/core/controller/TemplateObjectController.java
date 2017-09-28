package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.service.TemplateObjectService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
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
 *                   2017/8/28  Wen TieHu Create
 *                   2017/9/27  YoungerOu modified
 *                    before delete templateObject, delete templateObjectPro first.
 *                 </pre>
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
   * @param param    按照指定字段查询
   * @param pageNum  页面号
   * @param pageSize 页面大小
   * @param order    排序信息（字符串）
   * @return 返回查询结果
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryPropertiesPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    if (isValid(rowId)) {
      ServerResult serverResult = templateObjectService.queryTemplateProPage(rowId, search, param, pageNum, pageSize, order);
      return result(serverResult);
    }
    return result(new ServerResult().setStateMessage(STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }


  /**
   * 查询信息
   *
   * @param search 空格查询的值
   * @return PlatResult
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
    if (isValid(rowId)) {
      List<Map> maps = templateObjectService.selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
      return result(new ServerResult<>(maps));
    }
    return result(new ServerResult<>().setStateMessage(STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }

  /**
   * 通用查询方法
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult singleInputSelect(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(TemplateObject.class, order);
    Condition condition;
    if (isValid(param)) {
      condition = convertMapToAndConditionSeparatedByLike(TemplateObject.class, jsonToObj(param, Map.class));
    } else {
      condition = !isValid(search) ? null : createBlankQuery(blankSelectFields(), collectToSet(search));
    }
    PageResult<Map<String, Object>> pageResult;
    if (isValid(pageNum)) { // 分页查询
      pageResult = templateObjectService.selectPageMap(condition, orders, pageNum, pageSize);
    } else { // 查询所有
      pageResult = new PageResult(templateObjectService.selectMap(condition, orders));
    }
    return result(new ServerResult<>(pageResult));
  }

  /**
   * 通用新增方法
   *
   * @param map 接受一个实体对象
   * @return 返回操作信息
   */
  @RequestMapping("/add")
  public PlatResult insert(@RequestParam Map map) {
    ServerResult serverResult = new ServerResult();
    TemplateObject templateObject = new TemplateObject().fromMap(map).buildCreateInfo();
    int insert = templateObject.insert();
    if (-1 == insert) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.NEW_ADD_FAIL));
    }
    return result(new ServerResult<>(STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, templateObject));
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
    TemplateObject templateObject = new TemplateObject().fromMap(map).buildModifyInfo();
    int updateById = templateObject.updateById();
    if (-1 == updateById) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.UPDATE_FAIL));
    }
    return result(new ServerResult<>(STATUS_SUCCESS, Message.UPDATE_SUCCESS, templateObject));
  }

  /**
   * 通用删除方法
   *
   * @param rowId 按照rowId查询
   * @return 返回操作信息
   */
  @RequestMapping("/delete")
  public PlatResult delete(String rowId) {
    // 删除模板对象之前，先删除模板对象属性信息
    Condition preCondition = new ConditionBuilder(TemplateObjectPro.class).and().equal("templateObjRowId", rowId).endAnd().buildDone();
    templateObjectProService.delete(preCondition);
    Condition condition = new ConditionBuilder(TemplateObject.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<Map> maps = templateObjectService.selectMap(condition);
    ServerResult serverResult = new ServerResult();
    int status = new TemplateObject().buildDeleteInfo().deleteById(rowId);
    if (-1 == status) {
      return result(serverResult.setStateMessage(STATUS_FAIL, Message.DELETE_FAIL));
    }
    return result(new ServerResult<>(STATUS_SUCCESS, Message.DELETE_SUCCESS, maps));
  }
}
