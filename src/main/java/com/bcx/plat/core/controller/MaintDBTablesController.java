package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;

/**
 * 数据库表信息Controller层
 * Created by Wen Tiehu on 2017/8/11.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/maintTable")
public class MaintDBTablesController extends BaseController {

  @Autowired
  private MaintDBTablesService maintDBTablesService;

  protected List<String> blankSelectFields() {
    return Arrays.asList("tableSchema", "tableEname", "tableCname");
  }

  /**
   * 表信息分页查询方法
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult singleInputSelect(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(MaintDBTables.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否根据指定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(MaintDBTables.class, UtilsTool.jsonToObj(param, Map.class));
    } else { // 根据空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<MaintDBTables> maintDBTablesPageResult;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      maintDBTablesPageResult = maintDBTablesService.selectPage(condition, orders, pageNum, pageSize);
    } else {
      maintDBTablesPageResult = new PageResult(maintDBTablesService.selectMap(condition, orders));
    }

    ServerResult<PageResult<MaintDBTables>> pageResultServerResult = new ServerResult<>(maintDBTablesPageResult);
    return result(pageResultServerResult);
  }

  /**
   * 表信息查询方法
   *
   * @param search 按照空格查询
   * @return PlatResult
   */
  @RequestMapping("/query")
  public PlatResult singleInputSelect(String search) {
    Or blankQuery = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    List<Map<String, Object>> list = maintDBTablesService.singleSelect(MaintDBTables.class, blankQuery);
    if (list.size() > 0) {
      return result(new ServerResult<>(list));
    }
    return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
  }

  /**
   * 新增表信息属性
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping(value = "/add")
  public PlatResult addMaintDB(@RequestParam Map<String, Object> param) {
    ServerResult serverResult = maintDBTablesService.addMaintDB(param);
    return result(serverResult);
  }


  /**
   * 编辑业务对象属性
   *
   * @param param 实体参数
   * @return Map
   */
  @PostMapping(value = "/modify")
  public PlatResult modifyBusinessObjPro(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(param.get("rowId"))) {
      ServerResult serverResult = maintDBTablesService.modifyBusinessObjPro(param);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }

  /**
   * 通用删除方法
   *
   * @param rowId 按照rowId查询
   * @return Object
   */
  @PostMapping(value = "/delete")
  public PlatResult delete(String rowId) {
    if (!rowId.isEmpty()) {
      ServerResult delete = maintDBTablesService.delete(rowId);
      return result(delete);
    } else {
      return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }
}
