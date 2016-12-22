/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.page;

import com.appcore.model.AbstractObject;
import java.util.List;

public class Page<T> extends AbstractObject
{
  private int page;
  private int pageSize;
  private long count;
  public List<T> data;

  public Page()
  {
  }

  public Page(int pageIndex, int pageSize, long rowCount, List<T> data)
  {
    this.page = pageIndex;
    this.pageSize = pageSize;
    this.count = rowCount;
    this.data = data;
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public long getCount() {
    return this.count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public List<T> getData() {
    return this.data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}