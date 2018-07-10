/*
 * Copyright (c) 2014 Red Hat, Inc. and others
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

package com.zx.project.cardmanager.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link com.zx.project.cardmanager.entity.CreditCard}.
 *
 * NOTE: This class has been automatically generated from the {@link com.zx.project.cardmanager.entity.CreditCard} original class using Vert.x codegen.
 */
public class CreditCardConverter {

  public static void fromJson(JsonObject json, CreditCard obj) {
    if (json.getValue("amount") instanceof Number) {
      obj.setAmount(((Number)json.getValue("amount")).longValue());
    }
    if (json.getValue("bankId") instanceof Number) {
      obj.setBankId(((Number)json.getValue("bankId")).intValue());
    }
    if (json.getValue("billDate") instanceof Number) {
      obj.setBillDate(((Number)json.getValue("billDate")).intValue());
    }
    if (json.getValue("createDate") instanceof Number) {
      obj.setCreateDate(((Number)json.getValue("createDate")).longValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("repaymentDate") instanceof Number) {
      obj.setRepaymentDate(((Number)json.getValue("repaymentDate")).intValue());
    }
    if (json.getValue("tmpAmount") instanceof Number) {
      obj.setTmpAmount(((Number)json.getValue("tmpAmount")).longValue());
    }
    if (json.getValue("tmpInvalidDate") instanceof Number) {
      obj.setTmpInvalidDate(((Number)json.getValue("tmpInvalidDate")).longValue());
    }
    if (json.getValue("uid") instanceof Number) {
      obj.setUid(((Number)json.getValue("uid")).intValue());
    }
  }

  public static void toJson(CreditCard obj, JsonObject json) {
    if (obj.getAmount() != null) {
      json.put("amount", obj.getAmount());
    }
    json.put("bankId", obj.getBankId());
    json.put("billDate", obj.getBillDate());
    if (obj.getCreateDate() != null) {
      json.put("createDate", obj.getCreateDate());
    }
    json.put("id", obj.getId());
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    json.put("repaymentDate", obj.getRepaymentDate());
    if (obj.getTmpAmount() != null) {
      json.put("tmpAmount", obj.getTmpAmount());
    }
    if (obj.getTmpInvalidDate() != null) {
      json.put("tmpInvalidDate", obj.getTmpInvalidDate());
    }
    json.put("uid", obj.getUid());
  }
}