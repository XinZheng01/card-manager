package com.zx.project.cardmanager.handler;

import com.zx.project.cardmanager.common.BaseHandler;
import com.zx.project.cardmanager.entity.CreditCard;
import com.zx.project.cardmanager.kits.StringKit;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.redis.RedisClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreditCardHandler extends BaseHandler {

  private Vertx vertx;

  private RedisClient redisClient;

  public static final String REDIS_CREDIT_CARD_KEY = "redis.credit.card.key";

  public static final String REDIS_CREDIT_CARD_ID = "redis.credit.card.id";

  public CreditCardHandler(Vertx vertx, RedisClient redisClient) {
    this.vertx = vertx;
    this.redisClient = redisClient;
  }

  public void list(RoutingContext context) {
    Single<List<CreditCard>> single = redisClient.rxHvals(REDIS_CREDIT_CARD_KEY)
      .map(arr -> arr.stream().map(s -> new CreditCard((String) s))
        .collect(Collectors.toList()));
    sendResponse(context, single, Json::encode);
  }

  public void findOne(RoutingContext context) {
    String cardId = context.request().getParam("cardId");
    if (StringKit.isBlank(cardId)) {
      badRequest(context);
      return;
    }
    Maybe<CreditCard> maybe = redisClient.rxHget(REDIS_CREDIT_CARD_KEY, cardId)
      .toMaybe()
      .map(CreditCard::new);
    sendResponse(context, maybe, Json::encode);
  }

  public void add(RoutingContext context) {
    JsonObject cardJson = context.getBodyAsJson();
    if (Objects.isNull(cardJson)) {
      badRequest(context);
      return;
    }
    Single<Long> single = incr().flatMap(id -> {
      CreditCard card = new CreditCard(cardJson);
      card.setId(id.intValue());
      return redisClient.rxHset(REDIS_CREDIT_CARD_KEY, String.valueOf(id), Json.encode(card));
    });
    sendResponse(context, single, Json::encode);
  }

  public void update(RoutingContext context) {
    JsonObject cardJson = context.getBodyAsJson();
    String cardId = context.request().getParam("cardId");
    if (Objects.isNull(cardJson) || StringKit.isEmpty(cardId)) {
      badRequest(context);
      return;
    }
    CreditCard card = new CreditCard(cardJson);
    Maybe<CreditCard> creditCardMaybe = redisClient.rxHget(REDIS_CREDIT_CARD_KEY, cardId)
      .toMaybe()
      .map(s -> new CreditCard(s).merge(card))
      .flatMap(c -> redisClient.rxHset(REDIS_CREDIT_CARD_KEY, cardId, Json.encode(c)).flatMapMaybe(l -> Maybe.just(c)));
    sendResponse(context, creditCardMaybe, Json::encode);
  }

  public void delete(RoutingContext context) {
    sendResponse(context, redisClient.rxDel(REDIS_CREDIT_CARD_KEY).toCompletable(), this::noContent);
  }

  public void deleteOne(RoutingContext context) {
    String cardId = context.request().getParam("cardId");
    sendResponse(context, redisClient.rxHdel(REDIS_CREDIT_CARD_KEY, cardId).toCompletable(), this::noContent);
  }

  /**
   * ID自增
   * @return
   */
  private Single<Long> incr(){
    return redisClient.rxIncr(REDIS_CREDIT_CARD_ID);
  }
}
