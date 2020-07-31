package com.aneta.core.models.scripting.enginetypes;

public enum EngineNames {
  JAVASCRIPT("nashorn"),
  PYTHON("python"),
  GROOVY("groovy");

  private String engineName;

  EngineNames(String engineName) {
    this.engineName = engineName;
  }

  public String getEngineName() {
    return engineName;
  }
}
