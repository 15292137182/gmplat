package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.DataSetConfigService;
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
import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static com.bcx.plat.core.utils.UtilsTool.isValid;


/**
 * 数据集controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/dataSetConfig")
public class DataSetConfigController extends BaseController {

  @Autowired
  private DataSetConfigService dataSetConfigService;

  protected List<String> blankSelectFields() {
    return Arrays.asList("datasetCode", "datasetName", "datasetType", "datasetContent");
  }

  /**
   * 新增数据集
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult addDataSet(@RequestParam Map<String, Object> param) {
    DataSetConfig dataSetConfig = new DataSetConfig().buildCreateInfo().fromMap(param);
    int insert = dataSetConfig.insert();
    if (insert != -1) {
      return successData(NEW_ADD_SUCCESS, dataSetConfig);
    } else {
      return fail(NEW_ADD_FAIL);
    }
  }

  /**
   * 修改数据集数据
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyDataSet(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      DataSetConfig dataSetConfig = new DataSetConfig().buildModifyInfo().fromMap(param);
      int update = dataSetConfig.updateById();
      if (update != -1) {
        return successData(UPDATE_SUCCESS, dataSetConfig);
      } else {
        return fail(UPDATE_FAIL);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 业务对象属性删除方法
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String rowId) {
    Condition condition = new ConditionBuilder(DataSetConfig.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<DataSetConfig> dataSetConfigs = dataSetConfigService.select(condition);
    if (UtilsTool.isValid(rowId)) {
      DataSetConfig dataSetConfig = new DataSetConfig();
      int del = dataSetConfig.deleteById(rowId);
      if (del != -1) {
        return successData(Message.DELETE_SUCCESS, dataSetConfigs);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 根据数据集rowId查找数据
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  @SuppressWarnings("unchecked")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(DataSetConfig.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(DataSetConfig.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = dataSetConfigService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult<>(dataSetConfigService.selectMap(condition, orders));
    }
    if (isValid(result)) {
      return result(new ServerResult<>(result));
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据功能块rowId查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public Object queryById(String rowId) {
    List result = dataSetConfigService.selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
    if (result.size() == 0) {
      return fail(Message.QUERY_FAIL);
    }
    return result(new ServerResult<>(result.get(0)));
  }
}
