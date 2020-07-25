package com.aneta.services.scripting.event;

import com.aneta.services.scripting.entity.SysScript;

import java.util.UUID;

public class RunScriptEvent extends BaseEvent<UUID> {
  private SysScript script;
  public RunScriptEvent(SysScript script) {
    super(script.getId());
    this.script = script;
  }
}
