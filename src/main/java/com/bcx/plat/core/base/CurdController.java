package com.bcx.plat.core.base;

import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Menu;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: CurdController class</p>
 * <p>Description: 针对单表的增删改查</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/12  Wen TieHu Create </pre>
 */
public abstract class CurdController<S extends BaseService<E>, E extends BaseEntity<E>> extends BaseController {

  @Autowired
  private S s;


  protected abstract List<String> blankSelectFields();


  /**
   * @return 泛型 E 的 class
   */
  @SuppressWarnings("unchecked")
  private Class<E> getClassE() {
    return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }


  /**
   * 获取class实例
   *
   * @return E class实例
   */
  private E getInstance() {
    E e = null;
    try {
      e = getClassE().newInstance();
    } catch (InstantiationException | IllegalAccessException e1) {
      e1.printStackTrace();
    }
    return e;
  }

  /**
   * 新增
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult addMenu(@RequestParam Map<String, Object> param) {
    E e = getInstance().buildCreateInfo().fromMap(param);
    int insert = e.insert();
    if (insert != -1) {
      return successData(NEW_ADD_SUCCESS, getInstance());
    } else {
      return fail(NEW_ADD_FAIL);
    }
  }

  /**
   * 修改
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyMenu(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      E en = getInstance().buildModifyInfo().fromMap(param);
      int update = en.updateById();
      if (update != -1) {
        return successData(UPDATE_SUCCESS, getInstance());
      } else {
        return fail(UPDATE_FAIL);
      }
    } else {
      return fail(UPDATE_FAIL);
    }
  }

  /**
   * 根据rowId删除信息
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String rowId) {
    Condition condition = new ConditionBuilder(getClassE()).and().equal("rowId", rowId).endAnd().buildDone();
    List<E> select = s.select(condition);
    if (UtilsTool.isValid(rowId)) {
      int del = getInstance().deleteById(rowId);
      if (del == -1) {
        return fail(DELETE_FAIL);
      } else {
        return successData(Message.DELETE_SUCCESS, select.size() > 0 ? select.get(0) : "");
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 根据rowId查找数据
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @GetMapping("/queryPage")
  @SuppressWarnings("unchecked")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(getClassE(), order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Menu.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = s.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult<>(s.selectMap(condition, orders));
    }
    if (isValid(result)) {
      return result(new ServerResult<>(result));
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据rowId查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @GetMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (isValid(rowId)) {
      return result(new ServerResult<>(getInstance().selectOneById(rowId)));
    }
    return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
  }


}
