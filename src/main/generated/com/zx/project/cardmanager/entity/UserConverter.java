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
 * Converter for {@link com.zx.project.cardmanager.entity.User}.
 *
 * NOTE: This class has been automatically generated from the {@link com.zx.project.cardmanager.entity.User} original class using Vert.x codegen.
 */
public class UserConverter {

  public static void fromJson(JsonObject json, User obj) {
    if (json.getValue("createTime") instanceof Number) {
      obj.setCreateTime(((Number)json.getValue("createTime")).longValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("lastLoginTime") instanceof Number) {
      obj.setLastLoginTime(((Number)json.getValue("lastLoginTime")).longValue());
    }
    if (json.getValue("mobile") instanceof String) {
      obj.setMobile((String)json.getValue("mobile"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("password") instanceof String) {
      obj.setPassword((String)json.getValue("password"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("wxOpenid") instanceof String) {
      obj.setWxOpenid((String)json.getValue("wxOpenid"));
    }
  }

  public static void toJson(User obj, JsonObject json) {
    if (obj.getCreateTime() != null) {
      json.put("createTime", obj.getCreateTime());
    }
    json.put("id", obj.getId());
    if (obj.getLastLoginTime() != null) {
      json.put("lastLoginTime", obj.getLastLoginTime());
    }
    if (obj.getMobile() != null) {
      json.put("mobile", obj.getMobile());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    json.put("status", obj.getStatus());
    if (obj.getWxOpenid() != null) {
      json.put("wxOpenid", obj.getWxOpenid());
    }
  }
}