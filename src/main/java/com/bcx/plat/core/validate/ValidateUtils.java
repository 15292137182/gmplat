package com.bcx.plat.core.validate;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.SpringContextHolder;

import java.util.List;

/**
 * 校验工具类，主要负责对功能块的条件进行校验
 * <p>
 * Create By HCL at 2017/9/25
 */
public abstract class ValidateUtils {

  /**
   * 核心校验程序
   *
   * @param bean       需要校验的javaBean
   * @param frontRowId 功能块的rowId
   * @return 返回
   */
  public static CheckResult validate(BeanInterface bean, String frontRowId) {
    CheckResult checkResult = new CheckResult();
    List<FrontFuncPro> properties = getPropertiesByRowId(frontRowId);
    // 获取属性列表
    if (null != properties && !properties.isEmpty()) {
      properties.forEach(property -> {

      });
    }
    return checkResult;
  }



  /**
   * 根据功能块主键查询功能块的配置信息
   *
   * @param rowId 功能块主键
   * @return 功能块属性的集合
   */
  private static List<FrontFuncPro> getPropertiesByRowId(String rowId) {
    FrontFuncService service = SpringContextHolder.getBean(FrontFuncService.class);
    return service.selectPropertiesByRowId(rowId);
  }

}
