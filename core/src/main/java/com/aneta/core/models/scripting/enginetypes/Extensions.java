package com.aneta.core.models.scripting.enginetypes;

public enum Extensions {
  JAVASCRIPT("js"),
  PYTHON("py"),
  GROOVY("groovy");

  private String extensionType;

  Extensions(String extensionType) {
    this.extensionType = extensionType;
  }

  public String getExtensionType() {
    return extensionType;
  }
}
