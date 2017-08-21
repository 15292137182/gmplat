package com.bcx.plat.core.manager;

import com.bcx.BaseTest;
import org.junit.Test;

/**
 * Create By HCL at 2017/8/8
 */
public class SequenceManagerTest extends BaseTest {

  @Test
  public void test() {

    String s = SequenceManager.getInstance().buildSequenceNo("BusinObjPro", null);
    System.out.println(s);

  }
}