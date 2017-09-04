package com.bcx.plat.core.base.template;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.utils.SpringContextHolder;

/**
 * 基础 ORM 层，提供基本的方法
 * <p>
 * Create By HCL at 2017/9/4
 */
public abstract class BaseORM<T extends BeanInterface> implements BeanInterface<T> {

  /**
   * @return MoreBatis
   */
  private MoreBatis getMoreBatis() {
    return (MoreBatis) SpringContextHolder.getBean("moreBatis");
  }

}
