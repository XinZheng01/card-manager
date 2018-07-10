package com.zx.project.cardmanager.common;

public interface BaseEntity<Entity> {

  default <T> T getOrElse(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }

  Entity merge(Entity obj);
}
