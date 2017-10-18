package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;


/**
 * 前端功能模块属性Controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/fronFuncPro")
public class FrontFuncProController extends BaseController {

  @Autowired
  private FrontFuncProService frontFuncProService;

  /**
   * 模糊查询的字段
   *
   * @return 字段
   */
  protected List<String> blankSelectFields() {
    return Collections.singletonList("rowId");
  }


  /**
   * 前端功能块属性新增数据
   *
   * @param paramEntity 接受一个实体参数
   * @return PlatResult
   */
  @PostMapping(value = "/add")
  public PlatResult insert(@RequestParam Map<String, Object> paramEntity) {
    return result(frontFuncProService.addFrontPro(paramEntity));
  }

  /**
   * 通过功能块rowId查询功能块属性下对应的数据
   *
   * @param search 空格查询
   * @param rowId  功能块rowId
   * @return 返回serviceResult
   */
  @RequestMapping("/queryPro")
  public PlatResult singleQuery(String search, String rowId) {
    PlatResult platResult;
    if (UtilsTool.isValid(rowId)) {
      List<Map> frontFuncPros = frontFuncProService
          .selectMap(new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
              UtilsTool.createBlankQuery(Arrays.asList("funcCode", "funcName"), UtilsTool.collectToSet(search))));
      if (frontFuncPros.size() == 0) {
        platResult = fail(Message.QUERY_FAIL);
      } else {
        platResult = successData(Message.QUERY_SUCCESS, frontFuncPros);
      }
    } else {
      platResult = fail(Message.QUERY_FAIL);
    }
    return platResult;
  }


  /**
   * 根据功能块 rowId 查找当前对象下的所有属性并分页显示
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
      ServerResult result = frontFuncProService.queryProPage(rowId, search, param, pageNum, pageSize, order);
      platResult = result(result);
    } else {
      platResult = fail(Message.QUERY_FAIL);
    }
    return platResult;
  }


  /**
   * 删除功能块属性数据
   *
   * @param rowId 业务对象rowId
   * @return PlatResult
   */
  @PostMapping(value = "/delete")
  public PlatResult delete(String rowId) {
    PlatResult platResult;
    Condition condition = new ConditionBuilder(FrontFuncPro.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<Map> frontFuncPros = frontFuncProService.selectMap(condition);
    if (UtilsTool.isValid(rowId)) {
      FrontFuncPro frontFuncPro = new FrontFuncPro();
      int del = frontFuncPro.deleteById(rowId);
      if (del == -1) {
        platResult = fail(Message.DELETE_FAIL);
      } else {
        platResult = successData(Message.DELETE_SUCCESS, frontFuncPros);
      }
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
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
      FrontFuncPro frontFuncPro = new FrontFuncPro();
      FrontFuncPro modify = frontFuncPro.fromMap(param).buildModifyInfo();
      modify.setAttrSource(null);
      modify.setRelateBusiPro(null);
      int update = modify.updateById();
      if (update == -1) {
        platResult = fail(Message.UPDATE_FAIL);
      } else {
        platResult = successData(Message.UPDATE_SUCCESS, modify);
      }
    } else {
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
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
    ServerResult serverResult = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      Condition condition = new ConditionBuilder(FrontFuncPro.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<Map> select = frontFuncProService.selectMap(condition);
      if (select.size() == 0) {
        return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
      } else {
        return result(new ServerResult<>(select.get(0)));
      }
    } else {
      return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }
}
