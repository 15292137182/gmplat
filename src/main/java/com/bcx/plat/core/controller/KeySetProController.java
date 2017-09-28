package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.KeySetProService;
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

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p>Title: KeySetProController</p>
 * <p>Description: 键值集合属性数据属性明细控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>Histroy:
 *  2017/8/30  Wen TieHu Create
 *          </pre>
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySetPro")
@RestController
public class KeySetProController extends BaseController {

  @Autowired
  private KeySetProService keySetProService;

  protected List<String> blankSelectFields() {
    return Arrays.asList("confKey", "confValue");
  }


  /**
   * 键值集合属性数据新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/add", method = POST)
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    String confKey = String.valueOf(param.get("confKey")).trim();
    String confValue = String.valueOf(param.get("confValue")).trim();
    if (UtilsTool.isValid(confKey) && UtilsTool.isValid(confValue)) {
      Condition condition = new ConditionBuilder(KeySetPro.class).and()
          .equal("relateKeysetRowId", param.get("relateKeysetRowId"))
          .equal("confKey", confKey).endAnd().buildDone();
      List<KeySetPro> keySetPros = keySetProService.select(condition);
      if (keySetPros.size() == 0) {
        KeySetPro KeySetPro = new KeySetPro().buildCreateInfo().fromMap(param);
        int insert = KeySetPro.insert();
        if (insert != -1) {
          return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
          return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL));
        }
      }
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED));
    }
    return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY));
  }

  /**
   * 键值集合属性数据根据rowId修改数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/modify", method = POST)
  public PlatResult update(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(param.get("rowId"))) {
      String confKey = String.valueOf(param.get("confKey")).trim();
      String confValue = String.valueOf(param.get("confValue")).trim();
      if (UtilsTool.isValid(confKey) && UtilsTool.isValid(confValue)) {
        int update = keySetProService.singleUpdate(KeySetPro.class, param, new FieldCondition("rowId", Operator.EQUAL, param.get("rowId")));
        if (update != -1) {
          return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
        }
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
      }
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY));
    }
    return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }

  /**
   * 删除键值集合属性数据属性数据
   *
   * @param rowId 业务对象rowId
   * @return PlatResult
   */
  @RequestMapping(value = "/delete", method = POST)
  public PlatResult delete(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      KeySetPro keySetPro = new KeySetPro();
      int del = keySetPro.deleteById(rowId);
      if (del != -1) {
        return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS));
      } else {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
      }
    } else {
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
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
      Condition condition = new ConditionBuilder(KeySetPro.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<Map> select = keySetProService.selectMap(condition);
      if (select.size()==0) {
        return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
      }else{
        return result(new ServerResult<>(select.get(0)));
      }
    } else {
      return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
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
  public PlatResult singleInputSelect(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(KeySetPro.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否根据指定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(KeySetPro.class, UtilsTool.jsonToObj(param, Map.class));
    } else {  // 根据空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> keysetPro;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      keysetPro = keySetProService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      keysetPro = new PageResult(keySetProService.selectMap(condition, orders));
    }

    if (keysetPro.getResult() != null) {
      return result(new ServerResult<>(keysetPro));
    }
    return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
  }
}
