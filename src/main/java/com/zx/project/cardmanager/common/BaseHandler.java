package com.zx.project.cardmanager.common;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class BaseHandler {

  // Helper status methods.

  /**
   * Resolve an asynchronous status and send back the response.
   * By default, the successful status code is 200 OK.
   * 解析异步结果并发送回响应。
   * 成功的状态代码为 200 ok。
   *
   * @param context     routing context
   * @param asyncResult asynchronous status with no result
   */
  protected void sendResponse(RoutingContext context, Completable asyncResult) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(() -> ok(context), ex -> internalError(context, ex));
    }
  }

  /**
   * Resolve an asynchronous status and send back the response.
   * The successful status code depends on processor {@code f}.
   * 解析异步结果并发送回响应。
   * 成功的状态代码依赖于处理器{@code f}。
   *
   * @param context     routing context
   * @param asyncResult asynchronous status with no result
   * @param f           processor
   */
  protected void sendResponse(RoutingContext context, Completable asyncResult, Consumer<RoutingContext> f) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(() -> f.accept(context), ex -> internalError(context, ex));
    }
  }

  /**
   * Resolve an asynchronous result and send back the response.
   * The successful status code depends on processor {@code f}.
   * 解析异步结果并发送回响应。
   * 成功的状态代码依赖于处理器{@code f}。
   *
   * @param context     routing context
   * @param asyncResult asynchronous status with no result
   * @param f           processor
   * @param <T>         the type of result
   */
  protected <T> void sendResponse(RoutingContext context, Single<T> asyncResult, BiConsumer<RoutingContext, T> f) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(r -> f.accept(context, r), ex -> internalError(context, ex));
    }
  }

  /**
   * Resolve an asynchronous result and send back the response.
   * 解析异步结果并发送回响应。
   *
   * @param context     routing context
   * @param asyncResult asynchronous result
   * @param <T>         the type of result
   */
  protected <T> void sendResponse(RoutingContext context, Single<T> asyncResult) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(r -> ok(context, r),
        ex -> internalError(context, ex));
    }
  }

  protected <T> void sendResponse(RoutingContext context, Maybe<T> asyncResult) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      Single<Optional<T>> single = asyncResult.map(Optional::of)
        .switchIfEmpty(Maybe.just(Optional.empty()))
        .toSingle();
      sendResponseOpt(context, single);
    }
  }

  protected <T> void sendResponseOpt(RoutingContext context, Single<Optional<T>> asyncResult) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(r -> {
          if (r.isPresent()) {
            ok(context, r.get());
          } else {
            notFound(context);
          }
        },
        ex -> internalError(context, ex));
    }
  }

  /**
   * Send back a response with status 200 Ok.
   * 发送状态码为 200 ok 的响应
   *
   * @param context routing context
   */
  protected void ok(RoutingContext context) {
    ok(context, "");
  }

  /**
   * Send back a response with status 200 OK.
   * 发送状态码为 200 ok 的响应
   *
   * @param context routing context
   * @param content body content
   */
  protected <T> void ok(RoutingContext context, T content) {
    context.response().setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 200).put("data", content).encode());
  }

  /**
   * Send back a response with status 400 Bad Request.
   * 发送状态码为 400 bad request 的响应
   *
   * @param context routing context
   * @param ex      exception
   */
  protected void badRequest(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(400)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 400).put("msg", ex.getMessage()).encode());
  }

  /**
   * Send back a response with status 400 Bad Request.
   * 发送状态码为 400 bad request 的响应
   *
   * @param context routing context
   * @param cause   error message
   */
  protected void badRequest(RoutingContext context, String cause) {
    context.response().setStatusCode(400)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 400).put("msg", cause).encode());
  }

  /**
   * Send back a response with status 400 Bad Request.
   * 发送状态码为 400 bad request 的响应
   *
   * @param context routing context
   */
  protected void badRequest(RoutingContext context) {
    context.response().setStatusCode(400)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 400).put("msg", "bad_request").encode());
  }

  /**
   * Send back a response with status 404 Not Found.
   * 发送状态码为 404 not found 的响应
   *
   * @param context routing context
   */
  protected void notFound(RoutingContext context) {
    context.response().setStatusCode(404)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 404).put("msg", "not_found").encode());
  }

  /**
   * Send back a response with status 500 Internal Error.
   *
   * @param context routing context
   * @param ex      exception
   */
  protected void internalError(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 500).put("msg", ex.getMessage()).encode());
  }

  /**
   * Send back a response with status 500 Internal Error.
   *
   * @param context   routing context
   * @param errorCode error code
   * @param cause     error message
   */
  protected void internalError(RoutingContext context, int errorCode, String cause) {
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", errorCode).put("msg", cause).encode());
  }

  /**
   * Send back a response with status 500 Internal Error.
   *
   * @param context routing context
   * @param cause   error message
   */
  protected void internalError(RoutingContext context, String cause) {
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 500).put("msg", cause).encode());
  }

  /**
   * Send back a response with status 503 Service Unavailable.
   *
   * @param context routing context
   */
  protected void serviceUnavailable(RoutingContext context) {
    context.fail(503);
  }

  /**
   * Send back a response with status 503 Service Unavailable.
   *
   * @param context routing context
   * @param ex      exception
   */
  protected void serviceUnavailable(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(503)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 503).put("msg", ex.getMessage()).encode());
  }

  /**
   * Send back a response with status 503 Service Unavailable.
   *
   * @param context routing context
   * @param cause   error message
   */
  protected void serviceUnavailable(RoutingContext context, String cause) {
    context.response().setStatusCode(503)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("code", 503).put("msg", cause).encode());
  }
}
