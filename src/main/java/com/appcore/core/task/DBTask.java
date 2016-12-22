/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.core.task;

import com.appcore.dao.BaseDao;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBTask
  implements Runnable
{
  private static Logger LOGGER = LoggerFactory.getLogger(DBTask.class);
  public static final int SAVE = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;

  @Deprecated
  public static final int SAVE_OR_UPDATE = 4;
  private int type;
  private Object object;
  private Collection coll;
  private BaseDao baseDao;

  public DBTask(int type, Object object)
  {
    this.type = type;
    this.object = object;
    this.baseDao = this.baseDao;
  }

  public DBTask(int type, Collection coll) {
    this.type = type;
    this.coll = coll;
    this.baseDao = this.baseDao;
  }

  public void run()
  {
    if (this.object != null)
    {
      if (this.type == 1)
        this.baseDao.save(this.object);
      else if (this.type == 2)
        this.baseDao.update(this.object);
      else if (this.type == 3)
        this.baseDao.delete(this.object);
    }
    else if (this.coll != null)
    {
      if (this.type == 1)
        this.baseDao.saveAll(this.coll);
      else if (this.type == 2)
        this.baseDao.updateAll(this.coll);
      else if (this.type == 3) {
        this.baseDao.deleteAll(this.coll);
      }
    }
    else
      LOGGER.error("错误的更新任务！");
  }
}