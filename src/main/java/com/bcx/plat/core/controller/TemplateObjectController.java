package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * Title: TemplateObjectController</p>
 * Description: 模板对象控制层
 * Copyright: Shanghai BatchSight GMP Information of management platform, Inc. Copyright(c) 2017
 *
 * @author Wen TieHu
 * <pre>History:
 * 2017/8/28  Wen TieHu Create
 * </pre>
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
    List<Condition> ors = new ArrayList<>();
    if (isValid(rowId)) {
      ors.add(new FieldCondition("templateObjRowId", Operator.EQUAL, rowId));
      if (isValid(search)) {
        ors.add(createBlankQuery(blankSelectFields(), collectToSet(search)));
      }
      Condition condition = new Or(ors);
      List<Order> orders = dataSort(order);
      PageResult<Map<String, Object>> result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);
      return result(new ServerResult<>(STATUS_FAIL, Message.QUERY_FAIL, result));
    }
    return result(new ServerResult<>(STATUS_FAIL, Message.QUERY_FAIL, null));
  }

  /**
   * 查询信息
   *
   * @param search 空格查询的值
   * @return 返回
   */
  @RequestMapping("/query")
  public PlatResult singleInputSelect(String search) {
    Map<String, Object> responseMap = new HashMap<>();
    List<Map> maps = templateObjectService.selectMap(createBlankQuery(blankSelectFields(), collectToSet(search)));
    responseMap.put("data", maps);
    return result(new ServerResult<>(responseMap));
  }

  /**
   * 根据功能块rowId查询当前数据
   *
   * @param rowId  功能块rowId
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public Object queryById(String rowId) {
    Map<String, Object> responseMap = new HashMap<>();
    List<Map> maps = templateObjectService.selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
    responseMap.put("data", maps);
    return result(new ServerResult<>(responseMap));
  }

  /**
   * 通用查询方法
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param locale   国际化参数
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public Object singleInputSelect(String search,
                                  @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                  String order) {
    LinkedList<Order> orders = dataSort(order);
    pageNum = search == null || search.isEmpty() ? 1 : pageNum;
    return /*selectPage(locale, createBlankQuery(blankSelectFields(), collectToSet(search)), orders, pageNum, pageSize)*/ null;
  }


  /**
   * 通用新增方法
   *
   * @param request request请求
   * @param locale  国际化参数
   * @return 返回操作信息
   */
  @RequestMapping("/add")
  public Object insert(@RequestParam Map<String, String> param, HttpServletRequest request, Locale locale) {
    return /*super.insert(new TemplateObject().fromMap(param), request, locale)*/ null;
  }


  /**
   * 通过修改方法
   *
   * @param entity  接受一个实体参数
   * @param request request请求
   * @param locale  国际化参数
   * @return 返回操作信息
   */
  @RequestMapping("/modify")
  public Object update(Map entity, HttpServletRequest request, Locale locale) {
    return /*super.updateById(new TemplateObject().fromMap(entity), request, locale)*/ null;
  }

  /**
   * 通用删除方法
   *
   * @param rowId   按照rowId查询
   * @param request request请求
   * @param locale  国际化参数
   * @return 返回操作信息
   */
  @RequestMapping("/delete")
  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
    return /*deleteByIds(request, locale, rowId)*/null;
  }


}
