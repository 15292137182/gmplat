package com.bcx.plat.core.database.action.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表主键注解类
 * <p>
 * 基于字段的注解
 * Create By HCL at 2017/8/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TablePK {

}
