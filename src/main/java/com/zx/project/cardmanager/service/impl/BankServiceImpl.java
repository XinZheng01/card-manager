package com.zx.project.cardmanager.service.impl;

import com.zx.project.cardmanager.entity.Bank;
import com.zx.project.cardmanager.service.BankService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class BankServiceImpl implements BankService {


  @Override
  public BankService list(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
    return this;
  }

  @Override
  public BankService findOne(Handler<AsyncResult<Bank>> resultHandler) {
    return this;
  }
}
