package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.base.BaseConstants.LOGIC_DELETE;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Message.OPERATOR_SUCCESS;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.mapper.DBTableColumnMapper;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库字段服务类 Create By HCL at 2017/8/1
 */
@Service
@Transactional
public class DBTableColumnServiceImpl implements DBTableColumnService {

  @Autowired
  private DBTableColumnMapper dbTableColumnMapper;

  /**
   * 查询数据方法
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  public ServiceResult select(Map map) {
    return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS, dbTableColumnMapper.select(map));
  }

  /**
   * 查询数据库表中关联数据中字段信息
   *
   * @param rowId 查询条件
   * @return 返回查询结果
   */
  public ServiceResult selectByTableId(String rowId) {
    return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS,
        dbTableColumnMapper.selectByTableId(rowId));
  }

  /**
   * 新建 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  public ServiceResult insert(DBTableColumn bean) {
    bean.buildCreateInfo();
    return new ServiceResult<>(dbTableColumnMapper.insert(bean), OPERATOR_SUCCESS, bean.getRowId());
  }

  /**
   * 更新 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  public ServiceResult update(DBTableColumn bean) {
    bean.buildModifyInfo();
    return new ServiceResult<>(dbTableColumnMapper.update(bean), OPERATOR_SUCCESS, bean.getRowId());
  }

  /**
   * 删除 数据库字段信息
   *
   * @param map 数据表 bean
   * @return 操作结果状态
   */
  public ServiceResult batchDelete(Map map) {
    if (LOGIC_DELETE) {
      map.putAll(new BaseEntity<>().buildDeleteInfo().toMap());
      return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS,
          dbTableColumnMapper.batchLogicDelete(map));
    } else {
      return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS,
          dbTableColumnMapper.batchDelete(map));
    }
  }
}