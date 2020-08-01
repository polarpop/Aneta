package com.aneta.core.scripting;

import com.aneta.core.scripting.managers.ScriptingManager;
import com.aneta.core.scripting.models.AnetaScript;
import com.aneta.core.scripting.models.ScriptRequest;
import com.aneta.core.scripting.models.ScriptResponse;
import com.aneta.core.scripting.models.enginetypes.EngineNames;
import com.aneta.core.scripting.models.enginetypes.Extensions;
import com.aneta.core.scripting.models.enginetypes.MimeTypes;

import javax.script.*;


public class ScriptingEngine {
  private final ScriptingManager manager = new ScriptingManager();
  private ScriptEngine engine;
  private AnetaScript script;

  public ScriptingEngine() {}

  /**
   * Aneta's Scripting Engine. Houses the Manager, and engine.
   *
   * @param script The AnetaScript object.
   * @see javax.script.ScriptEngine
   * @see com.aneta.core.scripting.managers.ScriptingManager
   */
  public ScriptingEngine(AnetaScript script) {
    this.script = script;
  }

  public ScriptEngine getEngine() {
    return engine;
  }

  public void setEngineByExtension(Extensions extension) {
    this.engine = manager.getEngineByExtension(extension.getExtensionType());
  }

  public void setEngineByName(EngineNames engineName) {
    this.engine = manager.getEngineByName(engineName.getEngineName());
  }

  public void setEngineByMimeType(MimeTypes mimeType) {
    this.engine = manager.getEngineByMimeType(mimeType.getMimeType());
  }

  public ScriptResponse<?> getResponse() {
    return script.getResponse();
  }

  /**
   * Run the @link{#AnetaScript Script} object.
   * */
  public void run() {
    ScriptRequest req = script.getRequest();

    try {
      Object results = engine.eval(req.getScript());
      ScriptResponse<Object> res = new ScriptResponse<>(results);
      script.setHasResults(true);
      script.setResponse(res);
    } catch (ScriptException e) {
      ScriptResponse<Void> res = new ScriptResponse<>();
      res.setError(e);
      script.setHasResults(true);
      script.setResponse(res);
    }
  }
}
