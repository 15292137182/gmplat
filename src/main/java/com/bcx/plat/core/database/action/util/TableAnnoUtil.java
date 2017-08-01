package com.bcx.plat.core.database.action.util;

import com.bcx.plat.core.database.action.annotations.Table;
import com.bcx.plat.core.database.action.annotations.TablePK;
import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.utils.UtilsTool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.bcx.plat.core.utils.UtilsTool.getAnnoFieldName;

/**
 * Create By HCL at 2017/8/1
 */
public class TableAnnoUtil {
    private static final HashMap<Class,List<String>> pkRegister=new HashMap<>();
    private static final HashMap<Class,TableSource> tableSourceRegister=new HashMap<>();
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
        List<String> result = pkRegister.get(clazz);
        if (result==null) {
            result=getAnnoFieldName(clazz, TablePK.class);
            pkRegister.put(clazz,result);
            return result;
        }
        return result;
    }

    public static TableSource getTableSource(Class<?> clazz){
        TableSource result = tableSourceRegister.get(clazz);
        if (result==null) {
            result=clazz.getAnnotation(Table.class).value();
            tableSourceRegister.put(clazz,result);
        }
        return result;
    }
}
