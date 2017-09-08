package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 数据库表信息Controller层
 * Created by Wen Tiehu on 2017/8/11.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/maintTable")
public class MaintDBTablesController extends BaseController<MaintDBTablesService> {

  private BusinessObjectService businessObjectService;

  @Autowired
  public MaintDBTablesController(BusinessObjectService businessObjectService) {
    this.businessObjectService = businessObjectService;
  }

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("tableSchema", "tableEname", "tableCname");
  }

  @RequestMapping("/queryPage")
  public Object singleInputSelect(String search,
                                  @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                  String order,
                                  Locale locale, HttpServletRequest request) {
    LinkedList<Order> orders = dataSort(order);
    pageNum = search == null || search.isEmpty() ? 1 : pageNum;
    return selectPage(locale, createBlankQuery(blankSelectFields(), collectToSet(search)), orders, pageNum, pageSize);
  }

  /**
   * 通用新增方法
   *
   * @param entity  接受一个实体参数
   * @param request request请求
   * @param locale  国际化参数
   * @return 返回操作信息
   */
  @RequestMapping("/add")
  public Object insert(MaintDBTables entity, HttpServletRequest request, Locale locale) {
    return super.insert(entity, request, locale);
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
    return super.updateById(new MaintDBTables().fromMap(entity), request, locale);
  }

  /**
   * 通用删除方法
   *
   * @param rowId   按照rowId查询
   * @param request request请求
   * @param locale  国际化参数
   * @return Object
   */
  @RequestMapping("/delete")
  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
    AtomicReference<Map<String, Object>> map = new AtomicReference<>(new HashMap<>());
    if (UtilsTool.isValid(rowId)) {
      map.get().put("relateTableRowId", rowId);
      List<Map> relateTableRowId = businessObjectService.selectMap(new FieldCondition("relateTableRowId"
              , Operator.EQUAL, rowId));
      if (relateTableRowId.size() == 0) {
        List<Map> list = getService().selectMap(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
        if (UtilsTool.isValid(list)) {
          List<String> rowIds = list.stream().map((row) ->
                  (String) row.get("rowId")).collect(Collectors.toList());
          return super.deleteByIds(request, locale, rowIds);
        } else {
          return super.deleteByIds(request, locale, rowId);
        }
      }
    }
    return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE)), locale);
  }


}
