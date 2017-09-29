package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * <p>Title: TemplateObjectService</p>
 * <p>Description: 模板对象业务层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *          2017/8/28  Wen TieHu Create
 *          </pre>
 */
@Service
public class TemplateObjectService extends BaseService<TemplateObject> {
  @Autowired
  private TemplateObjectProService templateObjectProService;

  /**
   * 根据模板对象RowId查询模板属性下对应的数据
   *
   * @param rowId    模板对象唯一标示
   * @param search   搜索条件
   * @param param    精确查询
   * @param pageNum  一页显示条数
   * @param pageSize 页码
   * @param order    排序
   * @return ServerResult
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryTemplateProPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    if (!(rowId.contains("[") && rowId.contains("]"))) {
      List<Order> orders = dataSort(TemplateObjectPro.class, order);
      Condition condition = queryPTemplatePages(param, rowId, search);
      PageResult<Map<String, Object>> result;
      if (isValid(pageNum)) { // 判断是否分页查询
        result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);
      } else {
        List<Map> selectMap = templateObjectProService.selectMap(condition, orders);
        result = new PageResult(selectMap);
      }
      return new ServerResult<>(result);
    } else {
      List list = UtilsTool.jsonToObj(rowId, List.class);
      if (isValid(list)) {
        List<Map<String, Object>> lists = new ArrayList<>();
        PageResult<Map<String, Object>> result = null;
        for (Object li : list) {
          String row = String.valueOf(li);
          List<Order> orders = dataSort(TemplateObjectPro.class, order);
          Condition condition = queryPTemplatePages(param, row, search);
          if (isValid(pageNum)) { // 判断是否分页查询
            result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);
          } else {
            List<Map> maps = templateObjectProService.selectMap(condition, orders);
            result = new PageResult(maps);
          }
          List<Map<String, Object>> resultResult = result.getResult();
          lists.addAll(resultResult);
          result.setResult(lists);
        }
        return new ServerResult<>(result);
      }else{
        return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
      }
    }
  }


  /**
   * 数据封装
   *
   * @param param  按照指定字段查询
   * @param rowId  唯一标示
   * @param search 按照空格查询
   * @return Condition
   */
  @SuppressWarnings("unchecked")
  private Condition queryPTemplatePages(String param, String rowId, String search) {
    Condition condition;
    if (isValid(param)) { // 判断是否根据指定字段查询
      condition = new And(new FieldCondition("templateObjRowId", Operator.EQUAL, rowId),
          convertMapToAndConditionSeparatedByLike(TemplateObjectPro.class, jsonToObj(param, Map.class)));
    } else { // 根据空格查询
      if (isValid(search)) {
        condition = new ConditionBuilder(TemplateObjectPro.class)
            .and().equal("templateObjRowId", rowId)
            .or().addCondition(createBlankQuery(Arrays.asList("code", "cname", "ename", "valueType"), collectToSet(search))).endOr()
            .endAnd().buildDone();
      } else {
        condition = new ConditionBuilder(TemplateObjectPro.class).and().equal("templateObjRowId", rowId).endAnd().buildDone();
      }
    }
    return condition;
  }

}
