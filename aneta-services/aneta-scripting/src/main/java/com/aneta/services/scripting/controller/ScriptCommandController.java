package com.aneta.services.scripting.controller;

import com.aneta.services.scripting.entity.SysScript;
import com.aneta.services.scripting.service.command.ScriptCommandService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/sys_scripts")
public class ScriptCommandController {
  private final ScriptCommandService service;

  public ScriptCommandController(ScriptCommandService service) {
    this.service = service;
  }

  @PostMapping
  public CompletableFuture<?> createAccount(@RequestBody SysScript body) {
    return service.createScript(body);
  }

  @PostMapping(value = "/{scriptId}/run")
  public CompletableFuture<Void> runScript(@RequestParam(name = "scriptId") UUID id) {
    return service.runScript(id);
  }
}
