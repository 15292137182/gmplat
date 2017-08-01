package com.bcx.plat.database.action;

import com.bcx.BaseTest;
import com.bcx.plat.core.database.action.QueryActionLite;
import com.bcx.plat.core.database.action.mapper.TempSuitMapper;
import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.action.substance.Field;
import com.bcx.plat.core.database.action.substance.FieldCondition;
import com.bcx.plat.core.database.action.substance.condition.Operator;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QueryTest extends BaseTest {
    class TestTable implements TableSource {
        public String getTableSourceSqlFragment() {
            return "test";
        }
    }
    @Autowired
    SqlSession dao;

    public void setDao(SqlSession dao) {
        this.dao = dao;
    }

    @Test
    public void queryTest(){
        final Field fieldId = new Field("id");
        final Field fieldDemo1 = new Field("demo1", "测试字段1");
        final Field fieldDemo2 = new Field("demo2", "测试字段2");
        QueryActionLite queryActionLite=new QueryActionLite().select(fieldId, fieldDemo1, fieldDemo2)
                .where(new FieldCondition(fieldDemo1, Operator.IN, Arrays.asList(new String[]{"1","2","3","4","5"})));
        queryActionLite.setTableSource(new TestTable());
        List<Map<String, Object>> result = dao.getMapper(TempSuitMapper.class).select(queryActionLite);
        result.size();
    }
}
