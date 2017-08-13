package com.bcx.plat.core.morebatis.command;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.ImmuteField;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.ConditionTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.component.Field;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QueryAction {

  public static final Column ALL_FIELD = new ImmuteField(new Field("*"));
  private List<Column> columns;
  private TableSource tableSource;
  private Condition where;
  private ConditionTranslator translator;
  private MoreBatis app;

  public QueryAction( MoreBatis app,ConditionTranslator translator) {
    this.app = app;
    this.translator = translator;
  }

  public List<Object> getTranslatedCondition(){
    return translator.translate(where);
  }

  public ConditionTranslator getTranslator() {
    return translator;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public QueryAction select(Column... column) {
    return select(Arrays.asList(column));
  }

  public QueryAction select(List<Column> column) {
    setColumns(column);
    return this;
  }

  public QueryAction selectAll() {
    return select(ALL_FIELD);
  }

  public QueryAction where(Condition condition) {
    setWhere(condition);
    return this;
  }

  public QueryAction from(TableSource tableSource) {
    setTableSource(tableSource);
    return this;
  }

  public Condition getWhere() {
    return where;
  }

  public void setWhere(Condition where) {
    this.where = where;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public void setTableSource(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  public PageResult<Map<String, Object>> selectPage(int pageNum,int pageSize){
    Page<Map<String,Object>> pageTask = PageHelper.startPage(pageNum, pageSize);
    List<Map<String, Object>> result = execute();
    PageInfo pageInfo = new PageInfo(result);
    PageResult<Map<String, Object>> pageResult = new PageResult<>(result);
    pageResult.setTotal(pageInfo.getTotal());
    pageResult.setPageNum(pageInfo.getPageNum());
    pageResult.setPageSize(pageInfo.getPageSize());
    return pageResult;
  }

  public List<Map<String, Object>> execute(){
    return app.execute(this);
  }
}
