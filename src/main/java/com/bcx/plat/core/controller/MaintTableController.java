package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
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
@RequestMapping("core/maintTable")
public class MaintTableController extends BaseController {

  @Autowired
  private MaintTableService maintTableService;

  @RequestMapping("/query")
  public MappingJacksonValue select(String str, HttpServletRequest request,
      HttpServletResponse response) {
    ServiceResult<MaintTableInfo> result = maintTableService.selectMaint(str);
    MappingJacksonValue value = new MappingJacksonValue(
        objToJson(result));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }

  @RequestMapping("/queryBuId")
  public MappingJacksonValue selectById(String rowId, HttpServletRequest request,
      HttpServletResponse response) {
    ServiceResult<MaintTableInfo> result = maintTableService.selectById(rowId);
    MappingJacksonValue value = new MappingJacksonValue(
        objToJson(result));
    value.setJsonpFunction(
        isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
    return value;
  }


}
