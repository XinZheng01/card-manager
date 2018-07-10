package com.zx.project.cardmanager.entity;

import com.zx.project.cardmanager.common.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

@DataObject(generateConverter = true)
public class CreditCard implements Serializable, BaseEntity<CreditCard> {

  private int id;
  private int uid;
  private int bankId;
  private String name;
  private int billDate;
  private int repaymentDate;
  private Long amount;
  private Long tmpAmount;
  private Long tmpInvalidDate;
  private Long createDate;

  public CreditCard(JsonObject obj) {
    CreditCardConverter.fromJson(obj, this);
  }

  public CreditCard(String json) {
    CreditCardConverter.fromJson(new JsonObject(json), this);
  }

  public CreditCard(int id, int uid, int bankId, String name, int billDate, int repaymentDate, Long amount, Long tmpAmount, Long tmpInvalidDate, Long createDate) {
    this.id = id;
    this.uid = uid;
    this.bankId = bankId;
    this.name = name;
    this.billDate = billDate;
    this.repaymentDate = repaymentDate;
    this.amount = amount;
    this.tmpAmount = tmpAmount;
    this.tmpInvalidDate = tmpInvalidDate;
    this.createDate = createDate;
  }

  @Override
  public CreditCard merge(CreditCard card) {
    return new CreditCard(id,
      getOrElse(card.uid, uid),
      getOrElse(card.bankId, bankId),
      getOrElse(card.name, name),
      getOrElse(card.billDate, billDate),
      getOrElse(card.repaymentDate, repaymentDate),
      getOrElse(card.amount, amount),
      getOrElse(card.tmpAmount, tmpAmount),
      getOrElse(card.tmpInvalidDate, tmpInvalidDate),
      getOrElse(card.createDate, createDate));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public int getBankId() {
    return bankId;
  }

  public void setBankId(int bankId) {
    this.bankId = bankId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBillDate() {
    return billDate;
  }

  public void setBillDate(int billDate) {
    this.billDate = billDate;
  }

  public int getRepaymentDate() {
    return repaymentDate;
  }

  public void setRepaymentDate(int repaymentDate) {
    this.repaymentDate = repaymentDate;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public Long getTmpAmount() {
    return tmpAmount;
  }

  public void setTmpAmount(Long tmpAmount) {
    this.tmpAmount = tmpAmount;
  }

  public Long getTmpInvalidDate() {
    return tmpInvalidDate;
  }

  public void setTmpInvalidDate(Long tmpInvalidDate) {
    this.tmpInvalidDate = tmpInvalidDate;
  }

  public Long getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Long createDate) {
    this.createDate = createDate;
  }
}
