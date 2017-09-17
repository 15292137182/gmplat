package com.bcx.plat.core.morebatis.performance;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.performance.mybatis.MybatisOperation;
import com.bcx.plat.core.morebatis.phantom.Condition;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class PerformanceTest extends BaseTest{
    MybatisOperation mybatisOperation;
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    MoreBatis moreBatis;

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



    @Test
    public void select(){
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("a","it works");
        Timer timer=new Timer();
        for (int i=1000;i>0;i--) mybatisOperation.select(args);
        Long mybatis=timer.finished();
        Condition condition=new ConditionBuilder(BusinessObject.class).and().equal("a","b").endAnd().buildDone();
        QueryAction statement = moreBatis.select(BusinessObject.class).where(condition);
        timer=new Timer();
        for (int i=1000;i>0;i--) statement.execute();
        Long moreBatis=timer.finished();
        System.out.println("moreBatis:"+moreBatis);
        System.out.println("myBatis:"+mybatis);
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
