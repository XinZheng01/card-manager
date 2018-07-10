package com.zx.project.cardmanager.entity;

import com.zx.project.cardmanager.common.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

@DataObject(generateConverter = true)
public class Bank implements Serializable, BaseEntity<Bank> {

  private int id;
  private String name;
  private String code;
  private int status;

  public Bank(JsonObject obj) {
    BankConverter.fromJson(obj, this);
  }

  public Bank(String json) {
    BankConverter.fromJson(new JsonObject(json), this);
  }

  public Bank(int id, String name, String code, int status) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.status = status;
  }

  @Override
  public Bank merge(Bank obj) {
    return new Bank(id,
      getOrElse(obj.name, name),
      getOrElse(obj.code, code),
      getOrElse(obj.status, status));
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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
