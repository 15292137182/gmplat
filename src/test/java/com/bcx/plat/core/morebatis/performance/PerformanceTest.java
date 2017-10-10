package com.bcx.plat.core.morebatis.performance;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.performance.mybatis.MybatisOperation;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.translator.postgre.Translator;
import java.math.BigDecimal;
import java.util.Map;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
public class PerformanceTest extends BaseTest{
    MybatisOperation mybatisOperation;
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    MoreBatis moreBatis;

    Translator translator=new Translator();

    private int rowCount=100;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setMoreBatis(MoreBatis moreBatis) {
        this.moreBatis = moreBatis;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Before
    public void before(){
        sqlSessionFactory.getConfiguration().getMapperRegistry().addMapper(MybatisOperation.class);
        mybatisOperation=sqlSessionTemplate.getMapper(MybatisOperation.class);
    }

    public void setMybatisOperation(MybatisOperation mybatisOperation) {
        this.mybatisOperation = mybatisOperation;
    }



    public long[] select(int count){
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("a","it works");
        Timer timer=new Timer();
        for(int i=0;i< rowCount;i++) mybatisOperation.select(args);
        Long mybatis=timer.finished();
        Condition condition=new ConditionBuilder(BusinessObject.class).and().equal("a","b").endAnd().buildDone();
        QueryAction statement = moreBatis.select(BusinessObject.class).where(condition);
        timer=new Timer();
        for(int i=0;i< rowCount;i++) statement.execute();
        Long moreBatisTime=timer.finished();
//        timer=new Timer();
//        for (int i=10;i>0;i--) translator.translateQueryAction(statement,new LinkedList());
//        Long translate=timer.finished();
//        System.out.println("translator:"+translate);
        return new long[]{mybatis,moreBatisTime};
    }

    public long[] insert(int count){
        BusinessObject businessObject=new BusinessObject();
        businessObject.setObjectName("insert测试");
        businessObject.buildCreateInfo();
        Map<String, Object> map = businessObject.toDbMap();
        Timer timer=new Timer();
        for(int i=0;i< rowCount;i++) {
            map.put("rowId", "mybatis insert "+i+count);
            mybatisOperation.insert(map);
        }
        long mybatis=timer.finished();
        timer=new Timer();
        for(int i=0;i< rowCount;i++) {
            map.put("rowId", "morebatis insert "+i+count);
            moreBatis.insert(BusinessObject.class, map).execute();
        }
        long moreBatisTime=timer.finished();
        return new long[]{mybatis,moreBatisTime};
    }

    public long[] update(int count){
        HashMap<String,Object> map=new HashMap<>();
        map.put("objectName", "updated!");
        Timer timer=new Timer();
        for(int i=0;i< rowCount;i++) {
            map.put("rowId", "mybatis insert"+i+count);
            mybatisOperation.update(map);
        }
        long mybatis=timer.finished();
        timer=new Timer();
        Field rowId = moreBatis.getColumnByAlias(BusinessObject.class, "rowId");
        FieldCondition condition = new FieldCondition(rowId, Operator.EQUAL, null);
        for(int i=0;i< rowCount;i++) {
            map.put("rowId", "morebatis insert"+i+count);
            condition.setValue(map.get("rowId"));
            moreBatis.update(BusinessObject.class, map).where(condition).execute();
        }
        long moreBatisTime=timer.finished();
        return new long[]{mybatis,moreBatisTime};
    }

    @Test
    public void test(){
        LinkedList<long[]> insertResult=new LinkedList();
        LinkedList<long[]> selectResult=new LinkedList();
        LinkedList<long[]> updateResult=new LinkedList();
        for(int i=5;i>0;i--) insertResult.add(insert(i*100));
        for(int i=5;i>0;i--) selectResult.add(select(i*100));
        for(int i=5;i>0;i--) updateResult.add(update(i*100));
        long[] insertAvg=avg(insertResult);
        long[] selectAvg=avg(selectResult);
        long[] updateAvg=avg(updateResult);
    }

    private long[] avg(LinkedList<long[]> in){
        BigDecimal i1=new BigDecimal(0),i2=new BigDecimal(0);
        for (long[] longs : in) {
            i1=i1.add(new BigDecimal(longs[0]));
            i2=i2.add(new BigDecimal(longs[1]));
        }
        i1=i1.divide(new BigDecimal(in.size()));
        i2=i2.divide(new BigDecimal(in.size()));
        return new long[]{i1.longValue(),i2.longValue()};
    }

    class Timer{
        private Long start,end;

        public Timer() {
            start=System.currentTimeMillis();
        }

        public Long finished(){
            end=System.currentTimeMillis();
            return getDuration();
        }

        public Long getDuration(){
            return end-start;
        }
    }
}
