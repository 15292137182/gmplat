package com.bcx.plat.core.morebatis.typehandler;

import com.bcx.plat.core.utils.UtilsTool;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MapTypeHandler extends BaseTypeHandler<HashMap> {

  @Override
  public void setNonNullParameter(PreparedStatement preparedStatement, int i, HashMap map,
      JdbcType jdbcType) throws SQLException {
    preparedStatement.setString(i,UtilsTool.objToJson(map));
  }

  @Override
  public HashMap getNullableResult(ResultSet resultSet, String s) throws SQLException {
    return UtilsTool.jsonToObj(resultSet.getString(s), HashMap.class);
  }

  @Override
  public HashMap getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return UtilsTool.jsonToObj(resultSet.getString(i), HashMap.class);
  }

  @Override
  public HashMap getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    return UtilsTool.jsonToObj(callableStatement.getString(i), HashMap.class);
  }
}
