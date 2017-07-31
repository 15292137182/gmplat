package com.bcx.plat.core.sqlmapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Went on 2017/7/30.
 */
@Mapper
public interface TestMapper{

    List select(int id);


}
