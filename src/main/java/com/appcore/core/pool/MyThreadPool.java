/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.core.pool;

import java.io.PrintStream;
import java.util.LinkedList;

public class MyThreadPool
{
  private LinkedList<Runnable> queue = new LinkedList();
  private int size;

  public MyThreadPool(int size)
  {
    this.size = size;
    for (int i = 0; i < size; ++i)
      new WorkThread(null).start();
  }

  public synchronized Runnable getTask()
  {
    Runnable task = (Runnable)this.queue.poll();
    if (task == null) {
      try {
        System.out.println("wait...");
        super.wait();
        System.out.println("wait over...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return task;
  }

  public synchronized void excute(Runnable task) {
    System.out.println("excute...");
    this.queue.add(task);
    super.notifyAll();
  }

  public static void main(String[] args)
  {
    MyThreadPool threadPool = new MyThreadPool(4);
    for (int i = 0; i < 100; ++i)
    {
      threadPool.excute(new MyRunnable(null));
    }
  }

  private static class MyRunnable implements Runnable
  {
	  private Object obj;
	  public MyRunnable(Object obj){
		  this.obj = obj;
	  }
    public static int i;
    
    public void run()
    {
      i += 1;
      try {
        Thread.sleep(250L);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("i=====" + i);
    }
  }

  private class WorkThread extends Thread
  {
	  private Object obj;
	  public WorkThread(String obj){
		  this.obj = obj;
	  }
    public void run()
    {
      while (true)
      {
        Runnable task = MyThreadPool.this.getTask();
        if (task != null)
          task.run();
      }
    }
  }
}