package com.bcx.plat.core.base.beans;

import com.bcx.BaseTest;
import org.junit.Test;

import java.util.Map;

/**
 * Create By HCL at 2017/8/29
 */
public class TestBeanTest extends BaseTest {

  /**
   * 测试 TestBean 中的方法
   */
  @Test
  public void test() {
    BaseBean bb = new BaseBean().buildCreateInfo().buildDeleteInfo().buildModifyInfo();
    WorkFlowBean wb = new WorkFlowBean().buildApproveInfo("20");
    Map map = bb.toMap();
    map.putAll(wb.toMap());
    TestBean tb = new TestBean();
    map.put("lalalla", "lalallla");
    tb.fromMap(map);

    BaseBean bb1 = (BaseBean) tb.getSubAssembly(BaseBean.class);
    WorkFlowBean wb1 = (WorkFlowBean) tb.getSubAssembly(WorkFlowBean.class);

  }

}