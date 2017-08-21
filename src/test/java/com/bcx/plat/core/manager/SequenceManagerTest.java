package com.bcx.plat.core.manager;

import com.bcx.BaseTest;
import org.junit.Test;

/**
 * 测试序列号生成规则
 *
 * Create By HCL at 2017/8/8
 */
public class SequenceManagerTest extends BaseTest {

  /**
   * 测试流水号
   */
  @Test
  public void test() {

    String s = SequenceManager.getInstance().buildSequenceNo("keySet");
    System.out.println(s);

  }
}