package com.aneta.services.scripting.event.creation;

import com.aneta.services.scripting.entity.SysScript;
import com.aneta.services.scripting.event.BaseEvent;
import com.aneta.services.scripting.model.types.ScriptType;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class CreateScriptEvent extends BaseEvent<UUID> {
  private String label;
  private final SysScript sysScript;
  private ScriptType type = ScriptType.ON_DEMAND;

  public CreateScriptEvent(
          @NotNull SysScript script,
          ScriptType type) {
    super(script.getId());
    this.sysScript = script;

    this.type = type;
    this.sysScript.setScriptType(this.type);
  }

  public SysScript getSysScript() {
    return sysScript;
  }

  public ScriptType getType() {
    return type;
  }
}
