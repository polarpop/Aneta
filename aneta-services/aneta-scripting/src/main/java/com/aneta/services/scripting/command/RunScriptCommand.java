package com.aneta.services.scripting.command;

import com.aneta.services.scripting.model.types.ScriptType;

import java.util.UUID;

public class RunScriptCommand extends BaseCommand<UUID> {

  public RunScriptCommand(UUID id) {
    super(id);
  }
}
