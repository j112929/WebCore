/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.security;

import com.appcore.model.AbstractObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BmSession extends AbstractObject
{
  private String sessionId;
  private long expire;
  private boolean isNew;
  private ConcurrentMap<String, Object> map;

  public BmSession()
  {
    this.map = new ConcurrentHashMap(); }

  public long getExpire() {
    return this.expire; }

  public void setExpire(long expire) {
    this.expire = expire; }

  public String getSessionId() {
    return this.sessionId; }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId; }

  public boolean isNew() {
    return this.isNew; }

  public void setNew(boolean isNew) {
    this.isNew = isNew; }

  public ConcurrentMap<String, Object> getMap() {
    return this.map; }

  public void setMap(ConcurrentMap<String, Object> map) {
    this.map = map;
  }
}