package com.aneta.services.scripting.event.exception;

import com.aneta.services.scripting.event.BaseEvent;

import java.util.UUID;

public class RunScriptNotFoundEvent extends BaseEvent<UUID> {
  private String errorMsg;

  public RunScriptNotFoundEvent(UUID id) {
    super(id);
    this.errorMsg = "No Script found with that id " + id;
  }

  public String getErrorMsg() {
    return errorMsg;
  }
}
