package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.mapper.FrontFuncMapper;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bcx.plat.core.constants.Message.*;

/**
 * Created by Went on 2017/8/2.
 */
@Service
@Transactional
public class FromtFuncServiceImpl extends BaseService implements FrontFuncService {

  @Autowired
  private FrontFuncMapper frontFuncMapper;

  /**
   * 按照名称、代码、类型 条件查询
   *
   * @param map 接受参数
   * @return 返回查询结果
   */
  @Override
  public ServiceResult<FrontFunc> select(Map<String, Object> map) {
    List<FrontFunc> result = frontFuncMapper.select(map);
    ServiceResult serviceResult =null;
    for (int i = 0; i < result.size(); i++) {
      String objectCode = result.get(i).getObjectCode();
      String objectName = result.get(i).getObjectName();
      String tables = objectCode + "(" + objectName + ")";
      result.get(i).setTables(tables);
      serviceResult=  new ServiceResult(QUERY_SUCCESS, result);
    }
    if(serviceResult == null){
     serviceResult = new ServiceResult(QUERY_FAIL,"");
    }
    return serviceResult;
  }

  /**
   * 新增数据
   *
   * @param frontFunc 接受新增数据
   * @return 返回新增数据ID
   */
  @Override
  public ServiceResult<FrontFunc> insert(FrontFunc frontFunc) {
    frontFunc.buildCreateInfo();
    String rowId = frontFunc.getRowId();
    int insert = frontFuncMapper.insert(frontFunc);
    if(insert!=1){
      return  new ServiceResult<>(NEW_ADD_FAIL ,"");
    }
    return  new ServiceResult<>(NEW_ADD_SUCCESS ,rowId);

  }

  /**
   * 根据id修改数据
   * @param frontFunc 接受修改参数
   */
  @Override
  public ServiceResult<FrontFunc> update(FrontFunc frontFunc) {
    frontFunc.buildModifyInfo();
    int update = frontFuncMapper.update(frontFunc);
    if(update != 1){
      return  new ServiceResult<>(UPDATE_FAIL ,"");
    }
    return  new ServiceResult<>(UPDATE_SUCCESS ,"");
  }

  /**
   * 根据ID删除数据
   */
  @Override
  public ServiceResult<FrontFunc> delete(FrontFunc frontFunc) {
    frontFuncMapper.delete(frontFunc);
    return new ServiceResult<>(DELETE_SUCCESS,"");
  }
}
