package com.aneta.services.scripting.service;

import com.aneta.core.scripting.engine.ScriptingEngine;
import com.aneta.core.scripting.engine.model.ScriptingConstructorArgs;
import org.springframework.stereotype.Service;

@Service
public class ScriptingEngineService {

  public final ScriptingEngine engine;

  public ScriptingEngineService(ScriptingConstructorArgs args) {
    this.engine = new ScriptingEngine(args);
  }
}
