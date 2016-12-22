/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.core.pool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerService
{
  private static List<TimeTask> taskList = new CopyOnWriteArrayList();

  private static ExecutorService executorService = Executors.newCachedThreadPool();

  public static void scheduleOneTime(Runnable command, long initialDelay)
  {
    schedule(command, initialDelay, 2147483647L, 1);
  }

  public static void schedule(Runnable command, long initialDelay, long period)
  {
    schedule(command, initialDelay, period, 0);
  }

  public static void schedule(Runnable command, long initialDelay, long period, int runTimes)
  {
    long executeTime = System.currentTimeMillis() + initialDelay;

    TimeTask timeTask = new TimeTask(null);
    timeTask.setTask(command);
    timeTask.setExecuteTime(executeTime);
    timeTask.setPeriod(period);
    timeTask.setRunTimes(runTimes);

    taskList.add(timeTask);
  }

  private static void startLoopThread()
  {
    new Thread()
    {
      public void run() {
        TimerService.loop();
      }
    }
    .start();
  }

  private static void loop()
  {
    try
    {
      loopTaskList();
      Thread.sleep(1L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void loopTaskList()
  {
    long currentTime = System.currentTimeMillis();

    for (TimeTask timeTask : taskList)
      if (currentTime > timeTask.getExecuteTime())
      {
        timeTask.setExecuteTime(timeTask.getExecuteTime() + timeTask.getPeriod());

        if (timeTask.getRunTimes() > 0)
        {
          timeTask.setRunTimes(timeTask.getRunTimes() - 1);
          if (timeTask.getRunTimes() == 0)
          {
            taskList.remove(timeTask);
          }

        }

        executorService.execute(timeTask.getTask());
      }
  }

  static
  {
    startLoopThread();
  }

  private static class TimeTask
  {
    private Runnable task;
    private long executeTime;
    private long period;
    private int runTimes;
    private Object obj;
    public TimeTask(Object obj){
    	this.obj = obj;
    }
    public Runnable getTask()
    {
      return this.task; }

    public void setTask(Runnable task) {
      this.task = task; }

    public long getExecuteTime() {
      return this.executeTime; }

    public void setExecuteTime(long executeTime) {
      this.executeTime = executeTime; }

    public long getPeriod() {
      return this.period; }

    public void setPeriod(long period) {
      this.period = period; }

    public int getRunTimes() {
      return this.runTimes; }

    public void setRunTimes(int runTimes) {
      this.runTimes = runTimes;
    }
  }
}