/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.zx.project.cardmanager.service.reactivex;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.reactivex.RxGen(com.zx.project.cardmanager.service.BankService.class)
public class BankService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BankService that = (BankService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final io.vertx.lang.reactivex.TypeArg<BankService> __TYPE_ARG = new io.vertx.lang.reactivex.TypeArg<>(
    obj -> new BankService((com.zx.project.cardmanager.service.BankService) obj),
    BankService::getDelegate
  );

  private final com.zx.project.cardmanager.service.BankService delegate;
  
  public BankService(com.zx.project.cardmanager.service.BankService delegate) {
    this.delegate = delegate;
  }

  public com.zx.project.cardmanager.service.BankService getDelegate() {
    return delegate;
  }

  public BankService list(Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.list(resultHandler);
    return this;
  }

  public Single<List<JsonObject>> rxList() { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<List<JsonObject>>(handler -> {
      list(handler);
    });
  }


  public static  BankService newInstance(com.zx.project.cardmanager.service.BankService arg) {
    return arg != null ? new BankService(arg) : null;
  }
}
