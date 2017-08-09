package com.bcx.plat.core.manager;

import com.bcx.BaseTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Create By HCL at 2017/8/8
 */
public class SequenceManagerTest extends BaseTest {

  @Test
  public void test() {
    String code = "ABCDEFG";
    Map<String, Object> a = new HashMap<>();
    a.put("a", "测试");
    List s = SequenceManager.getInstance().produceSequenceNo(code, a, 5, true);
    System.out.println(s);
  }
}