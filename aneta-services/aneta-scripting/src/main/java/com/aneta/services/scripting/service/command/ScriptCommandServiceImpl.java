package com.aneta.services.scripting.service.command;

import com.aneta.services.scripting.command.creation.CreateScriptCommand;
import com.aneta.services.scripting.command.RunScriptCommand;
import com.aneta.services.scripting.entity.SysScript;
import com.aneta.services.scripting.model.types.ScriptType;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ScriptCommandServiceImpl implements ScriptCommandService {

  private final CommandGateway gateway;

  public ScriptCommandServiceImpl(CommandGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public CompletableFuture<SysScript> createScript(SysScript script) {
    return gateway.send(new CreateScriptCommand(
            script,
            java.util.Optional.of(ScriptType.ON_DEMAND)
    ));
  }

  @Override
  public CompletableFuture<Void> runScript(UUID id) {
    return gateway.send(new RunScriptCommand(id));
  }

  @Override
  public CompletableFuture<Void> runScripts(UUID[] scriptIds) {
    List<CompletableFuture<Void>> completables = Arrays.asList();

    for (int i=0;i<scriptIds.length;i++) {
      completables.add(gateway.send(new RunScriptCommand(scriptIds[i])));
    }
    return CompletableFuture.allOf(completables.toArray(new CompletableFuture[completables.size()]));
  }
}
