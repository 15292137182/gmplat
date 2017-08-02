package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.base.BaseConstants.LOGIC_DELETE;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Message.OPERATOR_SUCCESS;

import com.bcx.plat.core.base.BaseService;
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
public class DBTableColumnServiceImpl extends BaseService implements DBTableColumnService {

  @Autowired
  private DBTableColumnMapper dbTableColumnMapper;

  /**
   * 查询数据方法
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  @Override
  public ServiceResult<DBTableColumn> select(Map<String, Object> map) {
    return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS, dbTableColumnMapper.select(map));
  }

  /**
   * 新建 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  @Override
  public ServiceResult<DBTableColumn> insert(DBTableColumn bean) {
    bean.buildCreateInfo();
    return new ServiceResult<>(dbTableColumnMapper.insert(bean), OPERATOR_SUCCESS);
  }

  /**
   * 更新 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  @Override
  public ServiceResult<DBTableColumn> update(DBTableColumn bean) {
    bean.buildModifyInfo();
    return new ServiceResult<>(dbTableColumnMapper.update(bean), OPERATOR_SUCCESS);
  }

  /**
   * 删除 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  @Override
  public ServiceResult<DBTableColumn> delete(DBTableColumn bean) {
    if (LOGIC_DELETE) {
      bean.buildDeleteInfo();
      return new ServiceResult<>(dbTableColumnMapper.logicDelete(bean), OPERATOR_SUCCESS);
    } else {
      return new ServiceResult<>(dbTableColumnMapper.delete(bean), OPERATOR_SUCCESS);
    }
  }
}