package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * 前端功能模块属性Controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/fronFuncPro")
public class FrontFuncProController extends
    BaseController {

  @Autowired
  private TemplateObjectProService templateObjectProService;
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
   * 通用新增方法
   *
   * @param paramEntity 接受一个实体参数
   * @return
   */
  @RequestMapping(value = "/add", method = POST)
  public PlatResult insert(@RequestParam Map<String, Object> paramEntity) {
    ServerResult result = new ServerResult();
    String relateBusiPro = String.valueOf(paramEntity.get("relateBusiPro"));//关联对象属性
    List<FrontFuncPro> rowId = frontFuncProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));

    int insert = -1;
    if (!UtilsTool.isValid(rowId)) { // 如果没有关联对象属性，进行新增
      //判断关联对象属性是属于基本属性还是模板属性
      //从模板对象属性表中查询是否存在此属性，如果存在，则attrSource="module"，否则attrSource="base"
      List<TemplateObjectPro> templateObjectPros = templateObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
      if (UtilsTool.isValid(templateObjectPros)) {
        paramEntity.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_MODULE);
      } else {
        paramEntity.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_BASE);
      }
      // 进行新增
      FrontFuncPro frontFuncPro = new FrontFuncPro().buildCreateInfo().fromMap(paramEntity);
      insert = frontFuncPro.insert();
      if (insert == -1) {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL));
      } else {
        return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, frontFuncPro));
      }
    } else {//如果已存在关联对象属性的rowId，则直接返回提示信息
      return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DATA_QUOTE, insert));
    }
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
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      List<FrontFuncPro> frontFuncPros = frontFuncProService
          .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
              UtilsTool.createBlankQuery(Arrays.asList("funcCode", "funcName"), UtilsTool.collectToSet(search))));
//            frontFuncPros = queryResultProcess(frontFuncPros);
      if (frontFuncPros.size() == 0) {
        return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
      } else {
        ServerResult serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, frontFuncPros);
        return result(serverResult);
      }
    }
    return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));

  }


  /**
   * 根据功能块 rowId 查找当前对象下的所有属性并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryProPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    ServerResult serverResult = new ServerResult();
    LinkedList<Order> orders = UtilsTool.dataSort(FrontFuncPro.class, order);
    if (UtilsTool.isValid(rowId)) {
      Condition condition;
      if (UtilsTool.isValid(param)) { // 判断是否按照字段查询
        Map map = UtilsTool.jsonToObj(param, Map.class);
        condition = new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
            UtilsTool.convertMapToAndConditionSeparatedByLike(FrontFuncPro.class, map));
      } else {
        condition = new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
            UtilsTool.createBlankQuery(Collections.singletonList("displayTitle"), UtilsTool.collectToSet(search)));
      }
      PageResult<Map<String, Object>> pageResult;
      if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
        pageResult = frontFuncProService.selectPageMap(condition, orders, pageNum, pageSize);
      } else {
        pageResult = new PageResult(frontFuncProService.selectMap(condition, orders));
      }
      List<Map<String, Object>> list = frontFuncProService.queryProPage(pageResult.getResult());
      pageResult.setResult(list);
      return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, pageResult));
    }
    return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
  }


  /**
   * 删除功能块属性数据
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping(value = "/delete", method = POST)
  public PlatResult delete(String rowId) {
    Condition condition = new ConditionBuilder(FrontFuncPro.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<FrontFuncPro> frontFuncPros = frontFuncProService.select(condition);
    ServerResult result = new ServerResult();
    int del;
    if (!rowId.isEmpty()) {
      FrontFuncPro frontFuncPro = new FrontFuncPro();
      del = frontFuncPro.deleteById(rowId);
      if (del != -1) {
        return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS, frontFuncPros));
      } else {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
      }
    } else {
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
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
    int update;
    if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
      FrontFuncPro frontFuncPro = new FrontFuncPro();
      FrontFuncPro modify = frontFuncPro.fromMap(param).buildModifyInfo();
      modify.setAttrSource(null);
      modify.setRelateBusiPro(null);
      update = modify.updateById();
      if (update != -1) {
        return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, modify));
      } else {
        return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
      }
    }
    return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
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
    if (!rowId.isEmpty()) {
      Condition condition = new ConditionBuilder(FrontFuncPro.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<FrontFuncPro> select = frontFuncProService.select(condition);
      return result(new ServerResult<>(select));
    } else {
      return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }
}
