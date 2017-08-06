package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.FrontFuncPro;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Went on 2017/8/6.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FrontFuncProMapperTest extends BaseTest{

    @Autowired
    private FrontFuncProMapper frontFuncProMapper;

    static String newRowId = null;

    /**
     * 测试前端功能块属性Mapper接口的查询
     */
    @Test
    public void ATestSelect() {
        List<FrontFuncPro> select = frontFuncProMapper.select(null);
        for (int i = 0; i < select.size(); i++) {
            FrontFuncPro result = select.get(i);
            String rowId = result.getRowId();
            logger.info("++++++++++++" + rowId);
        }
    }

    /**
     * 测试前端功能块属性Mapper接口的新增
     */
    @Test
    public void BTestAdd() {
        FrontFuncPro result = new FrontFuncPro();

        result.buildCreateInfo();
        frontFuncProMapper.insert(result);
        newRowId = result.getRowId();
    }

    /**
     * 测试前端功能块属性Mapper接口的更新
     */
    @Test
    public void CupdateTest(){
        FrontFuncPro result = new FrontFuncPro();
        result.setAllowEmpty("001");
        result.setDisplayFunc("3232");
        result.setRowId(newRowId);
        result.buildModifyInfo();
        frontFuncProMapper.update(result);
    }

    /**
     * 测试前端功能块属性Mapper接口的删除
     */
    @Test
    public void DTestDel() {
        FrontFuncPro result = new FrontFuncPro();
        String rowId = newRowId;
        result.setRowId(rowId);
        result.getRowId();
        frontFuncProMapper.delete(result);
    }


}
