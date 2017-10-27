package com.bcx.plat.core.morebatis.command;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class QueryAction {

  private Collection<AliasedColumn> aliasedColumns;
  private TableSource tableSource;
  private Condition where;
  private List<Order> order;
  private List<FieldSource> group;
  private SqlComponentTranslator translator;
  private MoreBatis app;
  private Boolean distinct;

  public QueryAction(MoreBatis app, SqlComponentTranslator translator) {
    this.app = app;
    this.translator = translator;
  }

  public List<Order> getOrder() {
    return order;
  }

  public void setOrder(List<Order> order) {
    this.order = order;
  }

  public SqlComponentTranslator getTranslator() {
    return translator;
  }

  public Collection<AliasedColumn> getAliasedColumns() {
    return aliasedColumns;
  }

  public void setAliasedColumns(Collection<AliasedColumn> aliasedColumns) {
    this.aliasedColumns = aliasedColumns;
  }

  public List<FieldSource> getGroup() {
    return group;
  }

  public void setGroup(List<FieldSource> group) {
    this.group = group;
  }

  public QueryAction select(AliasedColumn... aliasedColumn) {
    return select(Arrays.asList(aliasedColumn));
  }

  public QueryAction select(Collection aliasedColumn) {
    setAliasedColumns(aliasedColumn);
    return this;
  }

  public QueryAction where(Condition condition) {
    setWhere(condition);
    return this;
  }

  public QueryAction distinct(){
    setDistinct(Boolean.TRUE);
    return this;
  }

  public QueryAction distinct(boolean distinct){
    setDistinct(distinct);
    return this;
  }

  public QueryAction from(TableSource tableSource) {
    setTableSource(tableSource);
    return this;
  }

  public QueryAction orderBy(Order... order) {
    setOrder(Arrays.asList(order));
    return this;
  }

  public QueryAction orderBy(List<Order> orders) {
    setOrder(orders);
    return this;
  }

  public QueryAction groupBy(FieldSource... fieldSources) {
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

  public PageResult<Map<String, Object>> selectPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Map<String, Object>> result = execute();
    PageInfo pageInfo = new PageInfo(result);
    PageResult<Map<String, Object>> pageResult = new PageResult<>(result);
    pageResult.setTotal(pageInfo.getTotal());
    pageResult.setPageNum(pageInfo.getPageNum());
    pageResult.setPageSize(pageInfo.getPageSize());
    return pageResult;
  }

  public Boolean getDistinct() {
    return distinct;
  }

  public void setDistinct(Boolean distinct) {
    this.distinct = distinct;
  }

  public List<Map<String, Object>> execute() {
    return app.execute(this);
  }
}
