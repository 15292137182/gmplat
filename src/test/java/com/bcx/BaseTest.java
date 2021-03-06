package com.bcx;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 基础测试类,加载Spring测试环境 但继承本类并不是必须的 在不继承该类的情况下，若要使用spring环境，请在class上加入注解：RunWith ContextConfiguration
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-application.xml",
    "classpath:spring/spring-mvc.xml"})
@Ignore // 由于该单元测试不由事务管理器创建，因此也不受事务管理器管理
@WebAppConfiguration
public class BaseTest {

  protected Logger logger = LoggerFactory.getLogger(getClass());

}