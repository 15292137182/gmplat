package com.bcx.plat.core.database.action.util;

import com.bcx.plat.core.database.action.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.bcx.plat.core.utils.UtilsTool.getAnnoFieldName;

/**
 * Create By HCL at 2017/8/1
 */
public class TableAnnoUtil {

    /**
     * 禁止以 new 的方式构造该函数
     */
    private TableAnnoUtil() {

    }

    /**
     * 获取带有 TablePk 注解的字段
     *
     * @param clazz clazz
     * @return 返回集合
     */
    public static List<String> getPkAnnoField(Class<?> clazz) {
        return getAnnoFieldName(clazz, TablePK.class);
    }
}
