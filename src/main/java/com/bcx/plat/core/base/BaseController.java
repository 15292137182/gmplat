package com.bcx.plat.core.base;

import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * 基础控制器
 */
public abstract class BaseController {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  protected ResourceBundleMessageSource messageSource;

  /**
   * 处理返回结果
   *
   * @param serverResult 服务处理结果
   * @return 平台包装后的结果
   */
  protected PlatResult result(ServerResult serverResult) {
    return PlatResult.success(serverResult);
  }

  /**
   * 适配转换 PageResult
   *
   * @param pageResult pageResult
   * @return 返回处理后的 Map
   */
  @SuppressWarnings("unchecked")
  protected Map adapterPageResult(PageResult<Map<String,Object>> pageResult) {
    Map result ;
    result = jsonToObj(objToJson(pageResult), HashMap.class);
    if (null != result) {
      result.put("data", result.get("result"));
      result.remove("result");
      return result;
    }
    return null;
  }
}
