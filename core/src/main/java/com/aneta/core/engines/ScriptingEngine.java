package com.aneta.core.engines;

import com.aneta.core.managers.ScriptingManager;
import com.aneta.core.models.scripting.enginetypes.EngineNames;
import com.aneta.core.models.scripting.enginetypes.Extensions;
import com.aneta.core.models.scripting.enginetypes.MimeTypes;

import javax.script.*;
import java.io.Reader;

/**
 * Aneta's Scripting Engine. Houses the Manager, and engine
 * @see javax.script.ScriptEngine
 * @see com.aneta.core.managers.ScriptingManager
 */
public class ScriptingEngine {
  private final ScriptingManager manager = new ScriptingManager();
  private ScriptEngine engine;
  private Reader reader;

  public ScriptingEngine() {}

  public ScriptEngine getEngine() {
    return engine;
  }

  public void setScript(Reader reader) {
    this.reader = reader;
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

  public void loadBindings(Bindings bindings) {
    if (engine != null) {
      engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
    }
  }

  public Object run() throws ScriptException {
    if (reader != null && engine != null) {
      return engine.eval(reader);
    }
    return null;
  }

  public Object run(Reader script) throws ScriptException {
    if (engine != null) {
      return engine.eval(script);
    }
    return null;
  }

  public Object run(Reader script, Bindings bindings) throws ScriptException {
    if (engine != null) {
      return engine.eval(script, bindings);
    }
    return null;
  }
}
