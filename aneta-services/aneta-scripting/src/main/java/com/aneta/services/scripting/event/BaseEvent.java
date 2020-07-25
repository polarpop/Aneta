package com.aneta.services.scripting.event;

public abstract class BaseEvent<ID> {
  protected ID id;

  public BaseEvent() {}

  public BaseEvent(ID id) {
    this.id = id;
  }

  protected ID getId() {
    return id;
  }

  protected void setId(ID id) {
    this.id = id;
  }
}
