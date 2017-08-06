package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.FrontFunc;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Went on 2017/8/6.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FrontFuncMapperTest extends BaseTest{

    @Autowired
    private FrontFuncMapper frontFuncMapper;

    static String newRowId = null;

    /**
     * 测试前端功能块Mapper接口的查询
     */
    @Test
    public void ATestSelect() {
        List<FrontFunc> select = frontFuncMapper.select(null);
        for (int i = 0; i < select.size(); i++) {
            FrontFunc result = select.get(i);
            String rowId = result.getRowId();
            logger.info("++++++++++++" + rowId);
        }
    }

    /**
     * 测试前端功能块Mapper接口的新增
     */
    @Test
    public void BTestAdd() {
        FrontFunc result = new FrontFunc();
        result.setFuncCode("001");
        result.setDesp("3232");
        result.setFuncName("12");
        result.buildCreateInfo();
        frontFuncMapper.insert(result);
        newRowId = result.getRowId();
    }

    /**
     * 测试前端功能块Mapper接口的更新
     */
    @Test
    public void CupdateTest(){
        FrontFunc result = new FrontFunc();
        result.setObjectCode("0001");
        result.setObjectName("0002");
        result.setRowId(newRowId);
        result.buildModifyInfo();
        frontFuncMapper.update(result);
    }

    /**
     * 测试前端功能块Mapper接口的删除
     */
    @Test
    public void DTestDel() {
        FrontFunc result = new FrontFunc();
        String rowId = newRowId;
        result.setRowId(rowId);
        result.getRowId();
        frontFuncMapper.delete(result);
    }


}
