package com.bcx.plat.core;

import com.bcx.plat.core.morebatis.EntityTest;
import java.lang.reflect.Method;
import org.springframework.util.ReflectionUtils;

public class SpringTest{

  public static void main(String[] args) {
    Method what = ReflectionUtils.findMethod(EntityTest.class, "entityTest");
    System.out.println(what.getName());
  }
}
