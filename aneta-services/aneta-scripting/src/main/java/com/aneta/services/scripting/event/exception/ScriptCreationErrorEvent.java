package com.aneta.services.scripting.event.exception;

import com.aneta.services.scripting.event.BaseEvent;

import java.util.UUID;

public class ScriptCreationErrorEvent extends BaseEvent<UUID> {
  private String errorMsg;

  public ScriptCreationErrorEvent(UUID id, String errorMsg) {
    super(id);
    this.errorMsg = errorMsg;
  }
}
