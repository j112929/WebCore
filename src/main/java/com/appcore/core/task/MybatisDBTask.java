/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.core.task;

import com.appcore.dao.MyBatisBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisDBTask implements Runnable {
	private static Logger LOGGER = LoggerFactory.getLogger(MybatisDBTask.class);
	public static final int INSERT = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;
	private int type;
	private Object object;
	private MyBatisBaseDao baseDao;

	public MybatisDBTask(int type, Object object, MyBatisBaseDao baseDao) {
		this.type = type;
		this.object = object;
		this.baseDao = baseDao;
	}

	public void run() {
		if (this.object != null) {
			if (this.type == 1)
				this.baseDao.insert(this.object);
			else if (this.type == 2)
				this.baseDao.update(this.object);
			else if (this.type == 3)
				this.baseDao.delete(this.object);
		} else
			LOGGER.error("错误的更新任务！");
	}
}