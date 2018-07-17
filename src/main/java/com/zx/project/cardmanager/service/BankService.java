package com.zx.project.cardmanager.service;

import com.zx.project.cardmanager.entity.Bank;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

@VertxGen
@ProxyGen
public interface BankService {

  @Fluent
  BankService list(Handler<AsyncResult<List<JsonObject>>> resultHandler);

  @Fluent
  BankService findOne(Handler<AsyncResult<Bank>> resultHandler);
}
