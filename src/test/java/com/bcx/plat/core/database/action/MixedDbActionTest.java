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

public class MixedDbActionTest extends BaseTest {
    @Autowired
    TempSuitMapper tempSuitMapper;

    public void setTempSuitMapper(TempSuitMapper tempSuitMapper) {
        this.tempSuitMapper = tempSuitMapper;
    }

    @Test
    public void MixedTest(){
        final FieldCondition idCondition = new FieldCondition("id", Operator.EQUAL, 999);
        final QueryAction queryAction=new QueryAction()
                .selectAll()
                .from(TableInfo.TEST)
                .where(idCondition);
        int sizeBefore=tempSuitMapper.select(queryAction).size();
        final Set<String> columns = Sets.newSet("id", "testFirst", "testSecond");
        Map<String,Object> row;
        List<Map<String,Object>> rows=new LinkedList<>();

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID().toString());
        row.put("testSecond", UUID.randomUUID().toString());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", "why???");
        row.put("testSecond", "这是为什么？");
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID().toString());
        row.put("testSecond", UUID.randomUUID().toString());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",999);
        row.put("testFirst", UUID.randomUUID().toString());
        row.put("testSecond", UUID.randomUUID().toString());
        rows.add(row);

        row=new HashMap<>();
        row.put("id",1000);
        row.put("testFirst", UUID.randomUUID().toString());
        row.put("testSecond", UUID.randomUUID().toString());
        rows.add(row);

        InsertAction insertAction=new InsertAction()
                .into(TableInfo.TEST)
                .cols(columns)
                .values(rows);
        tempSuitMapper.insert(insertAction);
        int sizeAfter=tempSuitMapper.select(queryAction).size();
        Assert.assertEquals(sizeAfter-sizeBefore,4);
        DeleteAction deleteAction=new DeleteAction().from(TableInfo.TEST).where(idCondition);
        Map<String,Object> args=new HashMap<>();
        args.put("id",2017);
        tempSuitMapper.delete(deleteAction);
        Assert.assertEquals(tempSuitMapper.select(queryAction).size(),0);
        final FieldCondition id1000 = new FieldCondition("id", Operator.EQUAL, 1000);
        QueryAction updatedQuery=new QueryAction().selectAll()
                .from(TableInfo.TEST)
                .where(id1000);
        final int id1000SizeBefore = tempSuitMapper.select(updatedQuery).size();
        UpdateAction updateAction=new UpdateAction().from(TableInfo.TEST).set(args).where(id1000);
        tempSuitMapper.update(updateAction);
        final int id1000SizeAfter = tempSuitMapper.select(updatedQuery).size();
        Assert.assertTrue(id1000SizeBefore>id1000SizeAfter);
    }
}