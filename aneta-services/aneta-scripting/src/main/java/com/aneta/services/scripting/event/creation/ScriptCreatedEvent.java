package com.aneta.services.scripting.event.creation;

import com.aneta.services.scripting.event.BaseEvent;

import java.util.UUID;

public class ScriptCreatedEvent extends BaseEvent<UUID> {
  public ScriptCreatedEvent(UUID id) {
    super(id);
  }
}
