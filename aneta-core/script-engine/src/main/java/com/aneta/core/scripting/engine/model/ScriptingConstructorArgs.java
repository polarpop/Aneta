package com.aneta.core.scripting.engine.model;

import com.aneta.core.scripting.engine.type.LanguageType;

import java.nio.file.Path;
import java.util.Optional;

public class ScriptingConstructorArgs {
  public Optional<String> script;
  public Optional<Path> path;
  public LanguageType language = LanguageType.JavaScript;
}
