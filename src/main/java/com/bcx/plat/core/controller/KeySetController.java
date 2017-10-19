package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.KeySetService;
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
import java.util.Objects;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.constants.Message.UPDATE_FAIL;
import static com.bcx.plat.core.constants.Message.UPDATE_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

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
    PlatResult platResult;
    if (UtilsTool.isValid(search)) {
      List list = UtilsTool.jsonToObj(search, List.class);
      ServerResult serverResult = keySetService.queryKeySet(list);
      platResult = result(serverResult);
    } else {
      platResult = fail(Message.QUERY_FAIL);
    }
    return platResult;
  }

  /**
   * 根据主表键值集合代码查询数据
   *
   * @param keyCode 键值集合代码
   * @param rowId   唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryKeyCode")
  public PlatResult queryKeyCode(String keyCode, String rowId) {
    PlatResult platResult;
    String row = String.valueOf(rowId);
    String keyCodes = String.valueOf(keyCode);
    if (!Objects.equals(row, "null") || !Objects.equals(keyCode, "null")) {
      ServerResult serverResult = keySetService.queryKeyCode(keyCodes, row);
      platResult = result(serverResult);
    } else {
      platResult = fail(Message.QUERY_FAIL);
    }
    return platResult;
  }

  /**
   * 根据键值集合主表查询从表详细信息
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryPro")
  public PlatResult queryPro(String rowId) {
    PlatResult platResult;
    if (UtilsTool.isValid(rowId)) {
      ServerResult<List<Map>> listServerResult = keySetService.queryPro(rowId);
      platResult = result(listServerResult);
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param rowId    唯一标识
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryProPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    PlatResult platResult;
    if (UtilsTool.isValid(rowId)) {
      LinkedList<Order> orders = UtilsTool.dataSort(KeySetPro.class, order);
      ServerResult serverResult = keySetService.queryProPage(search, rowId, param, pageNum, pageSize, orders);
      platResult = result(serverResult);
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 删除键值集合数据
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @PostMapping(value = "/delete")
  public PlatResult delete(String rowId) {
    PlatResult platResult;
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = keySetService.deletePro(rowId);
      platResult = result(serverResult);
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 键值集合新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @PostMapping(value = "/add")
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    PlatResult platResult;
    String keysetCode = String.valueOf(param.get("keysetCode")).trim();
    String keysetName = String.valueOf(param.get("keysetName")).trim();
    if (UtilsTool.isValid(keysetCode) && UtilsTool.isValid(keysetName)) {
      //获取代码做重复性判断
      Condition condition = new ConditionBuilder(KeySet.class).and().equal("keysetCode", keysetCode).endAnd().buildDone();
      List<KeySet> keySets = keySetService.select(condition);
      if (keySets.size() == 0) {
        KeySet keySet = new KeySet().buildCreateInfo().fromMap(param);
        if (keySet.insert() == -1) {
          platResult = fail(Message.NEW_ADD_FAIL);
        } else {
          platResult = success(Message.NEW_ADD_SUCCESS);
        }
      } else {
        platResult = fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      platResult = fail(Message.DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 键值代码重复性校验
   *
   * @param keysetCode 键值代码
   * @return platResult
   */
  @RequestMapping("/checkCode")
  public PlatResult checkCode(String keysetCode) {
    PlatResult platResult;
    Condition condition = new ConditionBuilder(KeySet.class).and().equal("keysetCode", keysetCode).endAnd().buildDone();
    List<KeySet> keySets = keySetService.select(condition);
    if (keySets.size() == 0) {
      platResult = success(Message.OPERATOR_SUCCESS);
    } else {
      platResult = fail(Message.DATA_CANNOT_BE_DUPLICATED);
    }
    return platResult;
  }

  /**
   * 键值集合根据rowId修改数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @PostMapping(value = "/modify")
  public PlatResult update(@RequestParam Map<String, Object> param) {
    PlatResult platResult;
    if (UtilsTool.isValid(param.get("rowId"))) {
      String keysetCode = String.valueOf(param.get("keysetCode")).trim();
      String keysetName = String.valueOf(param.get("keysetName")).trim();
      if (UtilsTool.isValid(keysetCode) && UtilsTool.isValid(keysetName)) {
        //获取代码做重复性判断
        Condition condition = new ConditionBuilder(KeySet.class).and().equal("keysetCode", keysetCode).endAnd().buildDone();
        List<KeySet> keySets = keySetService.select(condition);
        if (keySets.size() == 0) {
          KeySet keySet = new KeySet();
          KeySet modify = keySet.fromMap(param).buildModifyInfo();
          if (modify.updateById() == -1) {
            platResult = fail(UPDATE_FAIL);
          } else {
            platResult = success(UPDATE_SUCCESS);
          }
        } else {
          platResult = fail(Message.DATA_CANNOT_BE_DUPLICATED);
        }
      } else {
        platResult = fail(Message.DATA_CANNOT_BE_EMPTY);
      }
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 键值集合查询方法
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
  public PlatResult singleInputSelect(String search, String param, Integer pageNum, Integer pageSize, String order) {
    PlatResult platResult;
    LinkedList<Order> orders = dataSort(KeySet.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否按照定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(KeySet.class, UtilsTool.jsonToObj(param, Map.class));
    } else { // 按照空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> keySet;
    //判断是否分页查询
    if (UtilsTool.isValid(pageSize)) { // 分页查询
      keySet = keySetService.selectPageMap(condition, orders, pageNum, pageSize);
    } else { // 查询所有
      keySet = new PageResult(keySetService.selectMap(condition, orders));
    }
    if (isValid(keySet)) {
      platResult = result(new ServerResult<>(keySet));
    } else {
      platResult = fail(Message.QUERY_FAIL);
    }
    return platResult;
  }

  /**
   * 根据rowId查询数据
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    PlatResult platResult;
    if (UtilsTool.isValid(rowId)) {
      Condition condition = new ConditionBuilder(KeySet.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<Map> select = keySetService.selectMap(condition);
      if (select.size() == 0) {
        platResult = fail(Message.QUERY_FAIL);
      } else {
        platResult = result(new ServerResult<>(select.get(0)));
      }
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }
}
