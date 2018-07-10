package com.zx.project.cardmanager;

import com.zx.project.cardmanager.common.RestfulApiVerticle;
import com.zx.project.cardmanager.handler.BankHandler;
import com.zx.project.cardmanager.handler.CreditCardHandler;
import com.zx.project.cardmanager.handler.TodoHandler;
import com.zx.project.cardmanager.handler.UserHandler;
import io.reactivex.Completable;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends RestfulApiVerticle {

  public static final Logger log = LoggerFactory.getLogger(MainVerticle.class);

  private static final String HOST = "0.0.0.0";

  private static final int PORT = 8080;

  private BankHandler bankHandler;

  private CreditCardHandler creditCardHandler;

  private UserHandler userHandler;

  private TodoHandler todoHandler;

  private RedisClient redisClient;

  @Override
  public void start(Future<Void> startFuture) {
    final Router router = Router.router(vertx);
    // Enable HTTP Body parse.
    router.route().handler(BodyHandler.create());
    // Enable CORS.
    enableCorsSupport(router);
    initRedisServer().andThen(createHttpServer(router,
      config().getString("vert.http.host", HOST),
      config().getInteger("vert.http.port", PORT)))
      .toSingle(() -> redisClient)
      .flatMapCompletable((r) -> {
        userHandler = new UserHandler(vertx, r);
        bankHandler = new BankHandler(vertx, r);
        creditCardHandler = new CreditCardHandler(vertx, r);
        todoHandler = new TodoHandler(vertx, r);

        return userHandler.initData()
          .andThen(bankHandler.initData());
      })
      .subscribe(() -> {
        router(router);
        System.out.println("Server listening at: http://localhost:8080/");
        startFuture.complete();
      }, startFuture::fail);
  }

  private Completable initRedisServer(){
    RedisOptions options = new RedisOptions()
      .setHost(config().getString("vert.redis.host", "127.0.0.1"))
      .setPort(config().getInteger("vert.redis.port", 6379));
    return Completable.fromAction(() -> redisClient = RedisClient.create(vertx, options));
  }

  private void router(Router router){
    router.route("/api/*").consumes("application/json").produces("application/json");

    // todos
    router.get("/api/todos").handler(todoHandler::list);
    router.get("/api/todos/:todoId").handler(todoHandler::findOne);
    router.post("/api/todos").handler(todoHandler::add);
    router.patch("/api/todos/:todoId").handler(todoHandler::update);
    router.delete("/api/todos/:todoId").handler(todoHandler::deleteOne);
    router.delete("/api/todos").handler(todoHandler::delete);
    // banks
    router.get("/api/banks").handler(bankHandler::list);
    router.get("/api/banks/:bankId").handler(bankHandler::findOne);
    // credit-cards
    router.get("/api/cards").handler(creditCardHandler::list);
    router.get("/api/cards/:cardId").handler(creditCardHandler::findOne);
    router.post("/api/cards").handler(creditCardHandler::add);
    router.patch("/api/cards/:cardId").handler(creditCardHandler::update);
    router.delete("/api/cards/:cardId").handler(creditCardHandler::deleteOne);
    router.delete("/api/cards").handler(creditCardHandler::delete);
    // users
    router.get("/api/users").handler(userHandler::list);
    router.get("/api/users/:userId").handler(userHandler::findOne);
    router.post("/api/users").handler(userHandler::add);
    router.patch("/api/users/:userId").handler(userHandler::update);
    router.delete("/api/users/:userId").handler(userHandler::deleteOne);
    router.delete("/api/users").handler(userHandler::delete);
  }

  public static void main(String[] args) {
    System.setProperty("vertx.logger-delegate-factory-class-name",
      "io.vertx.core.logging.SLF4JLogDelegateFactory");
    Launcher.main(new String[]{"run", MainVerticle.class.getName(),
      "--conf", "src/main/resources/application.json",
//      "--redeploy", "src/main/java/com/zx/project/cardmanager/handler"
    });
//    Launcher.main(new String[]{"run", "-help"});
  }
}
