package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;

/**
 * 键值集合Controller层
 * Created by Went on 2017/8/3.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySet")
@RestController
public class KeySetController extends BaseController {

  private final KeySetService keySetService;

  @Autowired
  public KeySetController(KeySetService keySetService) {
    this.keySetService = keySetService;
  }

  protected List<String> blankSelectFields() {
    return Arrays.asList("keysetCode", "keysetName");
  }

  /**
   * 根据keysetCode查询，以数组的形式传入数据进来["demo","test"]
   *
   * @param search 按照空格查询
   * @return PlatResult
   * ["demo","test"]
   */
  @RequestMapping("/queryKeySet")
  public PlatResult queryKeySet(String search) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(search)) {
      List list = UtilsTool.jsonToObj(search, List.class);
      ServerResult serverResult = keySetService.queryKeySet(list);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }

  /**
   * 根据主表键值集合代码查询数据
   *
   * @param keyCode 按照空格查询
   * @return PlatResult
   */
  @RequestMapping("/queryKeyCode")
  public PlatResult queryKeyCode(String keyCode,String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(keyCode)) {
      ServerResult serverResult = keySetService.queryKeyCode(keyCode,rowId);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }

  /**
   * 根据键值集合主表查询从表详细信息
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryPro")
  public PlatResult queryPro(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult<List<Map>> listServerResult = keySetService.queryPro(rowId);
      return result(listServerResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryProPage(String rowId, String search,
                                 @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                 String order) {
    ServerResult result = new ServerResult();
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = keySetService.queryProPage(search, rowId, pageNum, pageSize, orders);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 删除键值集合数据
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public PlatResult delete(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = keySetService.deletePro(rowId);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }

  /**
   * 键值集合新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    //获取代码做重复性判断
    Condition condition = new ConditionBuilder(KeySet.class).and().equal("keysetCode", param.get("keysetCode")).endAnd().buildDone();
    List<KeySet> keySets = keySetService.select(condition);
    if (keySets.size() == 0) {
      KeySet keySet = new KeySet().buildCreateInfo().fromMap(param);
      if (UtilsTool.isValid(keySet.getKeysetName()) && keySet.insert() != -1) { // keysetName不为空才进行新增
        return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
      } else {
        return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL));
      }
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DATA_CANNOT_BE_DUPLICATED));
    }
  }

  /**
   * 键值代码重复性校验
   *
   * @param keysetCode 键值代码
   * @return platResult
   */
  @RequestMapping("/checkCode")
  public PlatResult checkCode(String keysetCode) {
    ServerResult result = new ServerResult();
    Condition condition = new ConditionBuilder(KeySet.class).and().equal("keysetCode", keysetCode).endAnd().buildDone();
    List<KeySet> keySets = keySetService.select(condition);
    if (keySets.size() == 0) {
      return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS));
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DATA_CANNOT_BE_DUPLICATED));
    }
  }

  /**
   * 键值集合根据rowId修改数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/modify", method = RequestMethod.POST)
  public PlatResult update(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(param.get("rowId"))) {
      KeySet keySet = new KeySet();
      KeySet modify = keySet.fromMap(param).buildModifyInfo();
      if (UtilsTool.isValid(modify.getKeysetName()) && modify.updateById() != -1) {
        return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
      } else {
        return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
      }
    }
    return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }


  /**
   * 键值集合查询方法
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult singleInputSelect(String search, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(order);
    Or blankQuery = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    PageResult<Map<String, Object>> keySet; // 后续判断初始化
    //判断是否分页查询
    if (UtilsTool.isValid(pageSize)) { // 分页查询
      keySet = keySetService.selectPageMap(blankQuery, orders, pageNum, pageSize);
    } else { // 查询所有
      keySet = new PageResult(keySetService.selectMap(blankQuery, orders));
    }
    return result(new ServerResult<>(keySet));
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
    if (UtilsTool.isValid(rowId)) {
      Condition condition = new ConditionBuilder(KeySet.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<KeySet> select = keySetService.select(condition);
      return result(new ServerResult<>(select));
    } else {
      return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }


}
