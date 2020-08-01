package com.aneta.core.scripting.models.enginetypes;

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
