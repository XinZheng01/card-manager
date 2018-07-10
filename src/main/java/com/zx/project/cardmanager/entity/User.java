package com.zx.project.cardmanager.entity;

import com.zx.project.cardmanager.common.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

@DataObject(generateConverter = true)
public class User implements Serializable, BaseEntity<User> {
  private int id;
  private String name;
  private String password;
  private String mobile;
  private int status;
  private Long createTime;
  private Long lastLoginTime;
  private String wxOpenid;

  public User(JsonObject obj) {
    UserConverter.fromJson(obj, this);
  }

  public User(String json) {
    UserConverter.fromJson(new JsonObject(json), this);
  }

  public User(int id, String name, String password, String mobile, int status, Long createTime, Long lastLoginTime, String wxOpenid) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.mobile = mobile;
    this.status = status;
    this.createTime = createTime;
    this.lastLoginTime = lastLoginTime;
    this.wxOpenid = wxOpenid;
  }

  @Override
  public User merge(User user) {
    return new User(id,
      getOrElse(user.name, name),
      getOrElse(user.password, password),
      getOrElse(user.mobile, mobile),
      getOrElse(user.status, status),
      getOrElse(user.createTime, createTime),
      getOrElse(user.lastLoginTime, lastLoginTime),
      getOrElse(user.wxOpenid, wxOpenid));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public Long getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Long lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getWxOpenid() {
    return wxOpenid;
  }

  public void setWxOpenid(String wxOpenid) {
    this.wxOpenid = wxOpenid;
  }
}
