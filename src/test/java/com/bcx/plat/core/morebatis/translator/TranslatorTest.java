package com.bcx.plat.core.morebatis.translator;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.builder.OrderBuilder;
import com.bcx.plat.core.morebatis.command.QueryAction;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.*;


public class TranslatorTest extends BaseTest{

    @Autowired
    private MoreBatis moreBatis;

    private Translator translator=new Translator();

    public void setMoreBatis(MoreBatis moreBatis) {
        this.moreBatis = moreBatis;
    }

    @Test
    public void testSelect(){
        QueryAction query = moreBatis.select(BusinessObject.class,BusinessObjectPro.class,"rowId","objRowId")
                .where(new ConditionBuilder(BusinessObject.class)
                .and().equal("rowId","10086").endAnd()
                .buildDone()).orderBy(new OrderBuilder(BusinessObject.class).asc("objectCode").desc("rowId").done());
        LinkedList result = translator.translateQueryAction(query, new LinkedList());
        Assert.assertTrue(result.getFirst()==Translator.SELECT);
    }
}