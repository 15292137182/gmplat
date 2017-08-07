package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.MaintTableInfo;
import com.bcx.plat.core.service.MaintTableService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Went on 2017/7/31.
 */
@RestController
@RequestMapping("/maint")
public class MaintTableController extends BaseController {

  @Autowired
  private MaintTableService maintTableService;

  @RequestMapping("/select")
  public MappingJacksonValue select(String str, HttpServletRequest request,
      HttpServletResponse response) {
    List result = maintTableService.selectMaint(str);
    MappingJacksonValue value = new MappingJacksonValue(
        objToJson(new ServiceResult(result,"消息传递成功")));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  @RequestMapping("/selectById")
  public MappingJacksonValue selectById(String rowId, HttpServletRequest request,
      HttpServletResponse response) {
    List<MaintTableInfo> result = maintTableService.selectById(rowId);
    MappingJacksonValue value = new MappingJacksonValue(
        objToJson(new ServiceResult(result,"消息传递成功")));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }


}
