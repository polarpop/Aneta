package com.aneta.core.models.scripting.enginetypes;

public enum MimeTypes {
  JAVASCRIPT("application/javascript"),
  PYTHON("application/python");

  private String mimeType;

  MimeTypes(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getMimeType() {
    return mimeType;
  }
}
