package com.aneta.core.scripting.engine;

import com.aneta.core.scripting.engine.model.ScriptingConstructorArgs;
import com.aneta.core.scripting.engine.type.LanguageType;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ScriptingEngine {
  private String script;
  private Path path;
  private ScriptEngine engine;
  private ScriptEngineManager manager = new ScriptEngineManager();

  public ScriptingEngine() {}

  public ScriptingEngine(ScriptingConstructorArgs args) {
    args.script.ifPresentOrElse(script -> this.script = script,
            () -> args.path.ifPresent(path -> this.path = path));
    setEngine(args.language);
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public void setEngine(LanguageType languageType) {
    this.engine = manager.getEngineByName(languageType.getName());
  }

  public CompletableFuture<?> run() throws ScriptException, FileNotFoundException {
    Invocable invocable = (Invocable) engine;
    if (script != null) {
      engine.eval(script);
    }

    if (path != null) {
      Reader reader = new FileReader(path.toFile());
      engine.eval(reader);
    }

    Object obj = engine.get("results");
    Runnable runnable = invocable.getInterface(obj, Runnable.class);
    return CompletableFuture.runAsync(runnable);
  }
}
