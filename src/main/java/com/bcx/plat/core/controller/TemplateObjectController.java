package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.service.TemplateObjectService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * <p>Title: TemplateObjectController</p>
 * <p>Description: 模板对象控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>Histroy:
 *                2017/8/28  Wen TieHu Create
 *          </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/templateObj")
public class TemplateObjectController extends BaseControllerTemplate<TemplateObjectService, TemplateObject> {


  private final TemplateObjectProService templateObjectProService;

  @Autowired
  public TemplateObjectController(TemplateObjectProService templateObjectProService) {
    this.templateObjectProService = templateObjectProService;
  }

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("templateCode", "templateCode", "templateName");
  }

  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param request  request请求
   * @param locale   国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryProPage")
  public Object queryProPage(String rowId,
                             String search,
                             @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                             String order,
                             HttpServletRequest request,
                             Locale locale) {
    LinkedList<Order> str = UtilsTool.dataSort(order);
    PageResult<Map<String, Object>> result ;

    if (UtilsTool.isValid(search)) {
      result = templateObjectProService.select(
              new ConditionBuilder(TemplateObjectPro.class).and()
                      .equal("templateObjRowId", rowId).or()
                      .addCondition(UtilsTool.createBlankQuery(Arrays.asList("code", "cname", "ename"),
                              UtilsTool.collectToSet(search))).endOr().endAnd().buildDone()
              ,  str, pageNum, pageSize);
      if (result.getResult().size() == 0) {
        return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
      }
      return super.result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
    }
    result =
            templateObjectProService.select(
                    new ConditionBuilder(TemplateObjectPro.class).and()
                            .equal("templateObjRowId", rowId).endAnd().buildDone()
                    ,  str, pageNum, pageSize);
    if (result.getResult().size() == 0) {
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }
    return super.result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
  }

}
