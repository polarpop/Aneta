package com.aneta.services.scripting.service.command;

import com.aneta.services.scripting.entity.SysScript;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ScriptCommandService {
  CompletableFuture<?> createScript(SysScript sysScript);
  CompletableFuture<Void> runScript(UUID scriptId);
  CompletableFuture<Void> runScripts(UUID[] scriptIds);
}
