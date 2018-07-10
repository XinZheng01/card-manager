package com.zx.project.cardmanager.handler;

import com.zx.project.cardmanager.common.BaseHandler;
import com.zx.project.cardmanager.entity.Todo;
import com.zx.project.cardmanager.kits.StringKit;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodoHandler extends BaseHandler {

  public static final Logger log = LoggerFactory.getLogger(TodoHandler.class);

  private Vertx vertx;

  private RedisClient redisClient;

  public static final String REDIS_TODO_KEY = "redis.todo.key";

  public static final String REDIS_TODO_ID = "redis.todo.id";

  public TodoHandler(Vertx vertx, RedisClient redisClient) {
    this.vertx = vertx;
    this.redisClient = redisClient;
  }

  /**
   * 列表
   * @param context
   */
  public void list(RoutingContext context){
    Single<List<Todo>> single = redisClient.rxHvals(REDIS_TODO_KEY)
      .map(arr -> arr.stream()
        .map(o -> new Todo((String) o))
        .collect(Collectors.toList())
      );
    sendResponse(context, single, Json::encode);
  }

  /**
   * 获取一个
   * @param context
   */
  public void findOne(RoutingContext context){
    String todoId = context.request().getParam("todoId");
    if (StringKit.isBlank(todoId)) {
      badRequest(context);
      return;
    }
    Maybe<Todo> maybe = redisClient.rxHget(REDIS_TODO_KEY, todoId)
      .toMaybe().map(Todo::new);
    sendResponse(context, maybe, Json::encode);
  }

  /**
   * 新增
   * @param context
   */
  public void add(RoutingContext context){
    JsonObject todoJson = context.getBodyAsJson();
    if (Objects.isNull(todoJson)){
      badRequest(context);
      return;
    }
    Single<Todo> single = incr()
      .flatMap(id -> {
        Todo todo = new Todo(todoJson);
        todo.setId(id.intValue());
        todo.setUrl(context.request().absoluteURI() + "/" + todo.getId());
        return redisClient.rxHset(REDIS_TODO_KEY, String.valueOf(todo.getId()), Json.encode(todo)).map(i -> todo);
      });
    sendResponse(context, single, Json::encode, this::created);
  }

  /**
   * 更新
   * @param context
   */
  public void update(RoutingContext context){
    try {
      JsonObject todoJson = context.getBodyAsJson();
      String todoId = context.request().getParam("todoId");
      if (StringKit.isBlank(todoId)){
        badRequest(context);
        return;
      }
      Todo todo = new Todo(todoJson);
      Maybe<Todo> todoMaybe = redisClient.rxHget(REDIS_TODO_KEY, todoId)
        .toMaybe()
        .map(s -> new Todo(s).merge(todo))
        .flatMap(s -> redisClient.rxHset(REDIS_TODO_KEY, todoId, Json.encode(s)).flatMapMaybe(l -> Maybe.just(s)));
      sendResponse(context, todoMaybe, Json::encode);
    } catch (Exception e) {
      badRequest(context, e);
    }

  }

  /**
   * 删除所有
   * @param context
   */
  public void delete(RoutingContext context){
    sendResponse(context, redisClient.rxDel(REDIS_TODO_KEY).toCompletable(), this::noContent);
  }

  /**
   * 删除一个
   * @param context
   */
  public void deleteOne(RoutingContext context) {
    String todoId = context.request().getParam("todoId");
    sendResponse(context, redisClient.rxHdel(REDIS_TODO_KEY, todoId).toCompletable(), this::noContent);
  }

  /**
   * ID自增
   * @return
   */
  private Single<Long> incr(){
    return redisClient.rxIncr(REDIS_TODO_ID);
  }

}
