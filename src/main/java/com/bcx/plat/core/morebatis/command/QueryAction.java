package com.bcx.plat.core.morebatis.command;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class QueryAction {
  //TODO 添加Context数据类型 并作为各command父类
  public static final Column ALL_FIELD = new Column() {
    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return "*";
    }

    @Override
    public String getAlies() {
      return null;
    }

    @Override
    public String getFieldSource() {
      return "*";
    }
  };
  private Collection<Column> columns;
  private TableSource tableSource;
  private Condition where;
  private List<Order> order;
  private List<FieldSource> group;
  private SqlComponentTranslator translator;
  private MoreBatis app;

  public QueryAction( MoreBatis app,SqlComponentTranslator translator) {
    this.app = app;
    this.translator = translator;
  }

  public List<Order> getOrder() {
    return order;
  }

  public void setOrder(List<Order> order) {
    this.order = order;
  }

  public List<Object> getTranslatedCondition(){
    return translator.translate(where);
  }

  public SqlComponentTranslator getTranslator() {
    return translator;
  }

  public Collection<Column> getColumns() {
    return columns;
  }

  public void setColumns(Collection<Column> columns) {
    this.columns = columns;
  }

  public List<FieldSource> getGroup() {
    return group;
  }

  public void setGroup(List<FieldSource> group) {
    this.group = group;
  }

  public QueryAction select(Column... column) {
    return select(Arrays.asList(column));
  }

  public QueryAction select(Collection<Column> column) {
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

  public QueryAction orderBy(Order ... order) {
    setOrder(Arrays.asList(order));
    return this;
  }

  public QueryAction orderBy(List<Order> orders) {
    setOrder(orders);
    return this;
  }

  public QueryAction groupBy(FieldSource ... fieldSources) {
    setGroup(Arrays.asList(fieldSources));
    return this;
  }

  public QueryAction groupBy(List<FieldSource> fieldSources) {
    setGroup(fieldSources);
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
