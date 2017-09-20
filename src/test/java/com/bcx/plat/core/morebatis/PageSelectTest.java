package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class PageSelectTest extends BaseTest {
    @Autowired
    MoreBatis moreBatis;

    public void setMoreBatis(MoreBatis moreBatis) {
        this.moreBatis = moreBatis;
    }

    @Test
    public void pageTest(){
        PageResult<Map<String, Object>> result = moreBatis.select(BusinessObject.class).selectPage(1, 4);
        long total = result.getTotal();
        System.out.println(total);
    }
}
