package com.bcx.plat.core.base.beans;

import com.bcx.BaseTest;
import org.junit.Test;

import java.util.Map;

/**
 * Create By HCL at 2017/8/29
 */
public class PublicBeanTest extends BaseTest {

  /**
   * 简单测试不
   */
  @Test
  public void test() {
    BaseBean bean = new BaseBean();
    Map map = bean.toMap();
    assert map.size() == 12;
  }

}