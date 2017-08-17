package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;

/**
 * 用户
 *
 * Create By HCL at 2017/8/17
 */
@Table(TableInfo.T_SYS_USER)
public class User extends BaseEntity<User> {

  @TablePK
  private String rowId;

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

}
