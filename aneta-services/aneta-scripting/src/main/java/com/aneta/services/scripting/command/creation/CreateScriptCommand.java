package com.aneta.services.scripting.command.creation;

import com.aneta.services.scripting.command.BaseCommand;
import com.aneta.services.scripting.entity.SysScript;
import com.aneta.services.scripting.model.types.ScriptType;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class CreateScriptCommand extends BaseCommand<UUID> {
  private ScriptType type = ScriptType.ON_DEMAND;
  private SysScript sysScript;

  public CreateScriptCommand(
          SysScript script,
          Optional<ScriptType> type) {

    super(script.getId());
    this.sysScript = script;
    type.ifPresent(scriptType -> this.type = scriptType);
    type.ifPresent(scriptType -> this.sysScript.setScriptType(scriptType));
  }

  public SysScript getSysScript() {
    return sysScript;
  }

  public ScriptType getType() {
    return type;
  }
}
