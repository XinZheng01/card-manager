package com.zx.project.cardmanager.handler;

import com.zx.project.cardmanager.common.BaseHandler;
import com.zx.project.cardmanager.entity.User;
import com.zx.project.cardmanager.kits.StringKit;
import io.reactivex.Completable;
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

public class UserHandler extends BaseHandler {

  private Vertx vertx;

  private RedisClient redisClient;

  public static final String REDIS_USER_KEY = "redis.user.key";

  public static final String REDIS_USER_ID = "redis.user.id";

  public UserHandler(Vertx vertx, RedisClient redisClient) {
    this.vertx = vertx;
    this.redisClient = redisClient;
  }

  public Completable initData() {
    return redisClient.rxHvals(REDIS_USER_KEY)
      .toMaybe()
      .filter(arr -> arr.size() == 0)
      .flatMap(arr -> incr().flatMapMaybe(l -> redisClient.rxHset(REDIS_USER_KEY, l.toString(),
        Json.encode(new User(l.intValue(), "郑鑫", "123456", "18665612354", 1, System.currentTimeMillis(), null, null))).toMaybe()))
      .flatMap(id -> incr().flatMapMaybe(l -> redisClient.rxHset(REDIS_USER_KEY, l.toString(),
        Json.encode(new User(l.intValue(), "马棉欢", "123456", "13717114571", 1, System.currentTimeMillis(), null, null))).toMaybe()))
      .flatMapCompletable(l -> Completable.complete());
  }

  public void list(RoutingContext context) {
    Single<List<User>> single = redisClient.rxHvals(REDIS_USER_KEY)
      .map(arr -> arr.stream()
        .map(s -> new User((String) s))
        .collect(Collectors.toList()));
    sendResponse(context, single, Json::encode);
  }

  public void findOne(RoutingContext context) {
    String userId = context.request().getParam("userId");
    if (StringKit.isEmpty(userId)) {
      badRequest(context);
      return;
    }
    Maybe<User> maybe = redisClient.rxHget(REDIS_USER_KEY, userId)
      .toMaybe().map(User::new);
    sendResponse(context, maybe, Json::encode);
  }

  public void add(RoutingContext context) {
    JsonObject jsonObj = context.getBodyAsJson();
    if (Objects.isNull(jsonObj)){
      badRequest(context);
      return;
    }
    Single<User> single = incr().flatMap(l -> {
      User user = new User(jsonObj);
      user.setId(l.intValue());
      return redisClient.rxHset(REDIS_USER_KEY, String.valueOf(l), Json.encode(user)).map(r -> user);
    });
    sendResponse(context, single, Json::encode);
  }

  public void update(RoutingContext context) {
    JsonObject jsonObj = context.getBodyAsJson();
    String userId = context.request().getParam("userId");
    if (Objects.isNull(jsonObj) || StringKit.isBlank(userId)){
      badRequest(context);
      return;
    }
    User user = new User(jsonObj);
    Maybe<User> maybe = redisClient.rxHget(REDIS_USER_KEY, userId)
      .toMaybe()
      .map(s -> new User(s).merge(user))
      .flatMap(u -> redisClient.rxHset(REDIS_USER_KEY, userId, Json.encode(u)).flatMapMaybe(r -> Maybe.just(u)));
    sendResponse(context, maybe, Json::encode);
  }

  public void delete(RoutingContext context) {
    sendResponse(context, redisClient.rxDel(REDIS_USER_KEY).toCompletable(), this::noContent);
  }

  public void deleteOne(RoutingContext context) {
    String userId = context.request().getParam("userId");
    sendResponse(context, redisClient.rxHdel(REDIS_USER_KEY, userId).toCompletable(), this::noContent);
  }

  /**
   * ID自增
   * @return
   */
  private Single<Long> incr(){
    return redisClient.rxIncr(REDIS_USER_ID);
  }

}
