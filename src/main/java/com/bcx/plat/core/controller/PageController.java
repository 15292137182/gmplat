package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Page;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.PageService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/11  Wen TieHu Create  </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX+"/core/page")
public class PageController extends BaseController {


  @Autowired
  private PageService pageService;

  protected List<String> blankSelectFields() {
    return Arrays.asList("pageNumber", "pageName", "pageUrl", "grantAuth", "belongModule", "remarks");
  }


  /**
   * 新增页面
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult addPage(@RequestParam Map<String, Object> param) {
    Page page = new Page().buildCreateInfo().fromMap(param);
    int insert = page.insert();
    if (insert != -1) {
      return successData(NEW_ADD_SUCCESS, page);
    } else {
      return fail(NEW_ADD_FAIL);
    }
  }

  /**
   * 修改页面
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyPage(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      Page page = new Page().buildModifyInfo().fromMap(param);
      int update = page.updateById();
      if (update != -1) {
        return successData(UPDATE_SUCCESS, page);
      } else {
        return fail(UPDATE_FAIL);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 根据rowId删除页面信息
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String rowId) {
    Condition condition = new ConditionBuilder(Page.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<Page> pages = pageService.select(condition);
    if (UtilsTool.isValid(rowId)) {
      Page page = new Page();
      int del = page.deleteById(rowId);
      if (del != -1) {
        return successData(Message.DELETE_SUCCESS, pages);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 根据页面rowId查找数据
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
    LinkedList<Order> orders = dataSort(Page.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Page.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = pageService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult<>(pageService.selectMap(condition, orders));
    }
    if (isValid(result)) {
      return result(new ServerResult<>(result));
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据页面rowId查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @GetMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (isValid(rowId)) {
      return result(new ServerResult<>(new Page().selectOneById(rowId)));
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }
}
