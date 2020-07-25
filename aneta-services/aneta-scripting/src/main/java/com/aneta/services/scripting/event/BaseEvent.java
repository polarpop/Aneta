package com.aneta.services.scripting.event;

public class BaseEvent<ID> {
  protected ID id;

  public BaseEvent() {}

  public BaseEvent(ID id) {
    this.id = id;
  }

  public ID getId() {
    return id;
  }

}
