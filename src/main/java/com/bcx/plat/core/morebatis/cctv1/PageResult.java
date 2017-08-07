package com.bcx.plat.core.morebatis.cctv1;

import java.util.List;

public class PageResult<T> {
  long total;
  int pageNum;
  int pageSize;
  List<T> result;

  public PageResult(List<T> result) {
    this.result = result;
  }

  public PageResult(long total, int pageNum, int pageSize, List<T> result) {
    this.total = total;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.result = result;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public List<T> getResult() {
    return result;
  }

  public void setResult(List<T> result) {
    this.result = result;
  }
}
