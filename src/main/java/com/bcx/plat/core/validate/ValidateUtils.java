package com.bcx.plat.core.validate;

import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.SpringContextHolder;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 校验工具类，主要负责对功能块的条件进行校验
 * <p>
 * Create By HCL at 2017/9/25
 */
public abstract class ValidateUtils {

  /**
   * 校验入口程序
   *
   * @param map        需要校验的数据
   * @param frontRowId 功能块的rowId
   * @return 返回
   */
  public static CheckResult validate(Map map, String frontRowId) {
    CheckResult checkResult = new CheckResult();
    List<FrontFuncPro> properties = getPropertiesByRowId(frontRowId);
    // 获取属性列表
    if (null != properties && !properties.isEmpty()) {
      properties.forEach(property -> doValidate(map, checkResult, property));
    }
    return checkResult;
  }

  /**
   * 执行校验程序，map是传入的数据，property 是某个字段的配置属性，需要从 property 中得到需要进行校验的字段，然后对数据进行校验
   * map 为 null 或者对应 map 中的值为 null 时，均认为该值是非法的 ，存在错误
   *
   * @param map         数据
   * @param checkResult 校验结果，校验的信息返回到 checkResult 中
   * @param property    属性信息
   */
  private static void doValidate(Map map, CheckResult checkResult, FrontFuncPro property) {
    String fieldName = getPropertyFieldName(property);
    if (isValid(fieldName)) {
      Object fieldValue = null == map ? null :
              (map.containsKey(fieldName) ? map.get(fieldName) : null);
      // 是否可以为空
      if (!Boolean.valueOf(property.getAllowEmpty()) && !isValid(fieldValue)) {
        checkResult.addError(new Error(fieldName, String.format("%s 不能为空！", fieldName)));
      }
    }
  }

  /**
   * @param pro 属性
   * @return 获取属性中对应的校验字段
   */
  private static String getPropertyFieldName(FrontFuncPro pro) {
    // TODO 等待实现中
    return "";
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
