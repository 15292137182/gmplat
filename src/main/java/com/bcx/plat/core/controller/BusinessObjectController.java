package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.BusinessRelateTemplate;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.TemplateObjectService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 业务对象controller层
 * Created by wth on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObj")
public class BusinessObjectController extends BaseController {

  @Autowired
  private BusinessObjectService businessObjectService;
  @Autowired
  private TemplateObjectService templateObjectService;

  public List<String> blankSelectFields() {
    return Arrays.asList("objectCode", "objectName");
  }

  /**
   * 业务对象新增方法
   *
   * @param param 接受一个实体参数
   * @return PlatResult
   */
  @RequestMapping("/add")
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    //新增业务对象数据
    ServerResult serverResult = businessObjectService.addBusiness(param);
    return result(serverResult);
  }

  /**
   * 根据业务对象rowId查询当前数据
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = businessObjectService.queryById(rowId);
      return result(serverResult);
    } else {
      return PlatResult.success(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 查询业务对象全部数据并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param param    按照指定字段查询
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult singleInputSelect(String search, int pageNum, int pageSize, String param, String order) {
    ServerResult result = businessObjectService.queryPagingBusinessObject(search, pageNum, pageSize, param, order);
    return result(result);
  }


  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param rowId    业务对象rowId
   * @param search   按照空格查询
   * @param param    按照指定参数查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryProPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(BusinessObjectPro.class, order);
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = businessObjectService.queryProPage(search, param, rowId, pageNum, pageSize, orders);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }


  /**
   * 执行变更操作
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @PostMapping(value = "/changeOperat")
  public PlatResult changeOperation(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS,
          businessObjectService.changeOperat(rowId));
      return PlatResult.success(serverResult);
    } else {
      logger.error("执行变更操作失败");
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 编辑业务对象
   *
   * @param paramEntity 接受一个实体参数
   * @return 返回操作信息
   */
  @PostMapping(value = "/modify")
  public PlatResult update(@RequestParam Map<String, Object> paramEntity) {
    ServerResult serverResult = new ServerResult();
    String rowId = paramEntity.get("rowId").toString();
    if (UtilsTool.isValid(rowId)) {
      BusinessObject businessObject = new BusinessObject().fromMap(paramEntity);
      businessObject.update(new FieldCondition("rowId", Operator.EQUAL, rowId));
      serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, businessObject);
      return result(serverResult);
    } else {
      return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }


  /**
   * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @PostMapping(value = "/delete")
  public PlatResult delete(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult delete = businessObjectService.delete(rowId);
      return super.result(delete);
    } else {
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
    }
  }

  /**
   * 根据业务对象唯一标识查询出业务关联模板属性的信息
   *
   * @param rowId 唯一标示
   * @param order 排序
   * @return PlatResult
   */
  @RequestMapping("/queryTemplatePro")
  public PlatResult queryTemplate(String rowId, String order) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      LinkedList<Order> orders = UtilsTool.dataSort(BusinessRelateTemplate.class, order);
      ServerResult<List<Map<String, Object>>> serverResult = businessObjectService.queryTemplatePro(rowId, orders);
      if (serverResult != null) {
        return super.result(serverResult);
      } else {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
      }
    } else {
      logger.error("查询出业务关联模板属性失败");
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 通用状态生效方法
   *
   * @param rowId 接受的唯一标示
   * @return PlatResult
   */
  @RequestMapping("/takeEffect")
  public PlatResult updateTakeEffect(String rowId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("status", BaseConstants.TAKE_EFFECT);
    BusinessObject businessObject = new BusinessObject();
    businessObject.setEtc(map);
    Condition condition = new ConditionBuilder(BusinessObject.class).and().equal("rowId", rowId).endAnd().buildDone();
    int update = businessObject.update(condition);
    if (-1 != update) {
      return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, map));
    }
    return result(new ServerResult<>(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL, map));
  }
}
