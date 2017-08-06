package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.MaintTableInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.INVALID;

/**
 * Created by Went on 2017/8/1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MaintTableMapperTest extends BaseTest{

    @Autowired
    private MaintTableMapper maintTableMapper;


    /**
     * 测试数据表Mapper接口的查询
     */
    @Test
    public void AselectTest(){
        List<MaintTableInfo> select = maintTableMapper.selectMaint(null);
        for (int i=0;i<select.size();i++){
            MaintTableInfo maintTableInfo = select.get(i);
            String rowId = maintTableInfo.getRowId();
            logger.info("++++++++++++"+rowId);
        }
    }

    /**
     * 测试数据表Mapper接口的查询
     */
    @Test
    public void BselectTest(){
        List<MaintTableInfo> select = maintTableMapper.selectById(null);
        for (int i=0;i<select.size();i++){
            MaintTableInfo maintTableInfo = select.get(i);
            String rowId = maintTableInfo.getRowId();
            logger.info("++++++++++++"+rowId);
        }
    }



}
