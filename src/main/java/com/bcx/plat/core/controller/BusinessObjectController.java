package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    }
    return PlatResult.success(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
  }

  /**
   * 查询业务对象全部数据并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult singleInputSelect(String search, Integer pageNum, Integer pageSize, String param, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(BusinessObject.class, order);
    Condition condition;
    Condition relateCondition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(BusinessObject.class, map);
    } else {
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    ServerResult serverResult = businessObjectService.queryPage(condition, pageNum, pageSize, orders);
    List<Map<String, Object>> result = ((PageResult) serverResult.getData()).getResult();
    for (Map<String, Object> results : result) {
      String relateTemplateObject = String.valueOf(results.get("relateTemplateObject"));
      List list = UtilsTool.jsonToObj(relateTemplateObject, List.class);
      if (null == list) {
        relateCondition = new ConditionBuilder(TemplateObject.class).and().equal("rowId", relateTemplateObject).endAnd().buildDone();
        List<TemplateObject> templateObjects = templateObjectService.select(relateCondition);
        if (templateObjects.size() > 0) {
          results.put("relateTemplate", templateObjects.get(0).getTemplateName());
        }
      } else {
        StringBuilder templates = new StringBuilder();
        for (Object li : list) {
          String valueOf = String.valueOf(li);
          relateCondition = new ConditionBuilder(TemplateObject.class).and().equal("rowId", valueOf).endAnd().buildDone();
          List<TemplateObject> templateObjects = templateObjectService.select(relateCondition);
          if (templateObjects.size() > 0) {
            for (TemplateObject temp : templateObjects) {
              templates.append(temp.getTemplateName()).append(",");
            }
          }
        }
        if (templates.lastIndexOf(",") != -1) {
          String substring = templates.substring(0, templates.length() - 1);
          results.put("relateTemplate", substring);
        }
      }
    }
    return super.result(serverResult);
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
  public PlatResult queryProPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(BusinessObjectPro.class, order);
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = businessObjectService.queryProPage(search, param, rowId, pageNum, pageSize, orders);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 执行变更操作
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping(value = "/changeOperat", method = RequestMethod.POST)
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
  @RequestMapping(value = "/modify", method = RequestMethod.POST)
  public PlatResult update(@RequestParam Map<String, Object> paramEntity) {
    String rowId = paramEntity.get("rowId").toString();
    ServerResult serverResult = null;
    if (UtilsTool.isValid(rowId)) {
      BusinessObject businessObject = new BusinessObject().fromMap(paramEntity);
      businessObject.update(new FieldCondition("rowId", Operator.EQUAL, rowId));
      serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, businessObject);
    }
    return super.result(serverResult);
  }


  /**
   * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
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
      LinkedList<Order> orders = UtilsTool.dataSort(order);
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
   * @return
   */
  @RequestMapping("/takeEffect")
  public Object updateTakeEffect(String rowId) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("rowId", rowId);
    map.put("status", BaseConstants.TAKE_EFFECT);
    map.put("modifyTime", UtilsTool.getDateTimeNow());
    BusinessObject businessObject = new BusinessObject();
//    businessObject.setBaseTemplateBean();
//    businessObjectService.update()
    return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, map));
  }
}
