package com.bcx.plat.core.database.action.annotations;

import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.info.TableInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表主键注解类
 * <p>
 * 基于字段的注解
 * Create By WJF at 2017/8/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    TableInfo value();
}