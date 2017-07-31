package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.service.TestService;
import com.bcx.plat.core.sqlmapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Went on 2017/7/30.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapperImpl;

    @Override
    public List testService(int id){
        List select = testMapperImpl.select(id);
        return select;
    }
}
