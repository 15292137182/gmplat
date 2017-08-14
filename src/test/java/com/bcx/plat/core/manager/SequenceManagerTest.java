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
      String s = SequenceManager.getInstance().buildSequenceNo(code, null);
      System.out.println(s);


    String content = "@{11111}&&${a;yyyy-MM-dd-;true}&&*{b;1}";
    List<String> list = SequenceManager.getInstance().mockSequenceNo(content, null, 5);
  }
}