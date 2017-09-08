package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.utils.PlatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;

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

  @RequestMapping("/queryProPage")
  public PlatResult queryPropertiesPage(String blankSearch) {
    return PlatResult.success(null);
  }
  /*
  public Object queryProPage(String rowId,
                             String search,
                             @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                             String order,
                             HttpServletRequest request,
                             Locale locale) {
    // 构造查询条件
    List<Condition> ors = new ArrayList<>();
    if (isValid(rowId)) {
      ors.add(new FieldCondition("templateObjRowId", Operator.EQUAL, rowId));
      if (isValid(search)) {
        ors.add(createBlankQuery(blankSelectFields(), collectToSet(search)));
      }
      Condition condition = new Or(ors);
      // 构建排序信息
      List<Order> orders = dataSort(order);
      // 返回结果
      PageResult<Map<String, Object>> result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);
      if (result.getResult().size() != 0) {
        return super.result(request, PlatResult.Msg(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
      }
    }
    return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
  }*/

  /**
   * 查询信息
   *
   * @param search 空格查询的值
   * @param locale 本地化信息
   * @return 返回
   */
  @RequestMapping("/query")
  public Object singleInputSelect(String search, Locale locale) {
    return /*selectList(locale, createBlankQuery(blankSelectFields(), collectToSet(search)))*/ null;
  }

  /**
   * 根据功能块rowId查询当前数据
   *
   * @param rowId  功能块rowId
   * @param locale 国际化参数
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public Object queryById(String rowId, Locale locale) {
    return /*selectList(locale, new FieldCondition("rowId", Operator.EQUAL, rowId))*/ null;
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
                                  String order,
                                  Locale locale, HttpServletRequest request) {
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
