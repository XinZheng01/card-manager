package com.zx.project.cardmanager.entity;


import com.zx.project.cardmanager.common.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import java.io.Serializable;

@DataObject(generateConverter = true)
public class Todo implements Serializable, BaseEntity<Todo> {

  private int id;
  private String title;
  private Boolean completed = Boolean.FALSE;
  private Integer order;
  private String url;

  public Todo(JsonObject obj){
    TodoConverter.fromJson(obj, this);
  }

  public Todo(String json){
    TodoConverter.fromJson(new JsonObject(json), this);
  }

  public Todo(int id, String title, Boolean completed, Integer order, String url) {
    this.id = id;
    this.title = title;
    this.completed = completed;
    this.order = order;
    this.url = url;
  }

  @Override
  public Todo merge(Todo todo) {
    return new Todo(id,
      getOrElse(todo.title, title),
      getOrElse(todo.completed, completed),
      getOrElse(todo.order, order),
      url);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
