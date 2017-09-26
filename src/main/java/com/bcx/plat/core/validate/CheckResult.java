package com.bcx.plat.core.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By HCL at 2017/9/21
 */
public class CheckResult {

  private List<Error> errors = new ArrayList<>();

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
   * 添加错误
   *
   * @param error 错误
   * @return 返回自身
   */
  public CheckResult addError(Error error) {
    this.errors.add(error);
    return this;
  }

}
