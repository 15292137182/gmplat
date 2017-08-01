package com.bcx.plat.core.database.action;

import com.bcx.BaseTest;
import com.bcx.plat.core.database.action.mapper.TempSuitMapper;
import com.bcx.plat.core.database.action.substance.FieldCondition;
import com.bcx.plat.core.database.action.substance.condition.Operator;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.utils.UtilsTool;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.*;

public class InsertActionTest
//        extends BaseTest
{
    @Autowired
    TempSuitMapper tempSuitMapper;

    public void setTempSuitMapper(TempSuitMapper tempSuitMapper) {
        this.tempSuitMapper = tempSuitMapper;
    }

//    @Test
    public void insertTest(){
        final QueryAction queryAction=new QueryAction()
                .selectAll()
                .from(TableInfo.TEST)
                .where(new FieldCondition("id", Operator.EQUAL,999));
        int sizeBefore=tempSuitMapper.select(queryAction).size();
        final Set<String> columns = Sets.newSet("id", "testFirst", "testSecond");
        Map<String,Object> row;
        List<Map<String,Object>> rows=new LinkedList<>();

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID());
        row.put("testSecond", UUID.randomUUID());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID());
        row.put("testSecond", UUID.randomUUID());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID());
        row.put("testSecond", UUID.randomUUID());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID());
        row.put("testSecond", UUID.randomUUID());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID());
        row.put("testSecond", UUID.randomUUID());
        rows.add(row);

        InsertAction insertAction=new InsertAction()
                .into(TableInfo.TEST)
                .cols(columns)
                .values(rows);
        tempSuitMapper.insert(insertAction);
        int sizeAfter=tempSuitMapper.select(queryAction).size();
        Assert.assertEquals(sizeAfter-sizeBefore,5);
    }
}