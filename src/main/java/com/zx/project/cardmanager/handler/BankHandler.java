package com.zx.project.cardmanager.handler;


import com.zx.project.cardmanager.common.BaseHandler;
import com.zx.project.cardmanager.common.annotation.Get;
import com.zx.project.cardmanager.common.annotation.Path;
import com.zx.project.cardmanager.entity.Bank;
import com.zx.project.cardmanager.kits.StringKit;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.Json;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.redis.RedisClient;

import java.util.List;
import java.util.stream.Collectors;

public class BankHandler extends BaseHandler {

  private Vertx vertx;

  private RedisClient redisClient;

  public static final String REDIS_BANK_KEY = "redis.bank.key";

  public BankHandler(Vertx vertx, RedisClient redisClient) {
    this.vertx = vertx;
    this.redisClient = redisClient;
  }

  public Completable initData(){
    return redisClient.rxHvals(REDIS_BANK_KEY)
      .toMaybe()
      .filter(arr -> arr.size() == 0)
      .flatMap(arr -> redisClient.rxHset(REDIS_BANK_KEY, "1", Json.encode(new Bank(1, "中国邮政储蓄", "100", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "2", Json.encode(new Bank(2, "工商银行", "102", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "3", Json.encode(new Bank(3, "农业银行", "103", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "4", Json.encode(new Bank(4, "中国银行", "104", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "5", Json.encode(new Bank(5, "建设银行", "105", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "6", Json.encode(new Bank(6, "交通银行", "301", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "7", Json.encode(new Bank(7, "中信银行", "302", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "8", Json.encode(new Bank(8, "光大银行", "303", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "9", Json.encode(new Bank(9, "华夏银行", "304", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "10", Json.encode(new Bank(10, "民生银行", "305", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "11", Json.encode(new Bank(11, "广发银行", "306", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "12", Json.encode(new Bank(12, "招商银行", "308", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "13", Json.encode(new Bank(13, "兴业银行", "309", 1))).toMaybe())
      .flatMap(l -> redisClient.rxHset(REDIS_BANK_KEY, "14", Json.encode(new Bank(14, "浦发银行", "310", 1))).toMaybe())
      .flatMapCompletable(l -> Completable.complete());
  }

  public void list(RoutingContext context) {
    Single<List<Bank>> listSingle = redisClient.rxHvals(REDIS_BANK_KEY)
      .map(arr -> arr.stream().map(o -> new Bank((String) o))
        .collect(Collectors.toList())
      );
    sendResponse(context, listSingle, Json::encode);
  }

  public void findOne(RoutingContext context) {
    String bankId = context.request().getParam("bankId");
    if (StringKit.isBlank(bankId)) {
      badRequest(context);
      return;
    }
    Maybe<Bank> bankMaybe = redisClient.rxHget(REDIS_BANK_KEY, bankId)
      .toMaybe()
      .map(Bank::new);
    sendResponse(context, bankMaybe, Json::encode);
  }

}
