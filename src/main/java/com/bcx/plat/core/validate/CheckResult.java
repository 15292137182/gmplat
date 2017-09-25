package com.bcx.plat.core.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By HCL at 2017/9/21
 */
public class CheckResult {

  private List<Error> errors;

  public CheckResult() {
    init();
  }

  /**
   * @return 是否存在错误
   */
  public boolean hasError() {
    return null != errors && !errors.isEmpty();
  }

  /**
   * @return 获取错误集合
   */
  public List<Error> getErrors() {
    return errors;
  }

  /**
   * 初始化，为 error 集合赋初始值
   */
  private void init() {
    errors = new ArrayList<>();
  }
}
