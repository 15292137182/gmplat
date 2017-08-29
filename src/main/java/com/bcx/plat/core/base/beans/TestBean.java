package com.bcx.plat.core.base.beans;

/**
 * Create By HCL at 2017/8/29
 */
public class TestBean<T extends TestBean> extends AssemblyBeanUtils<T> {

  public TestBean() {
    registerBean(WorkFlowBean.class, BaseBean.class);
  }
}
