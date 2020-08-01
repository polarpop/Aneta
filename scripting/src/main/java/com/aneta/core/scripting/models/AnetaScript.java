package com.aneta.core.scripting.models;

public class AnetaScript {
  private ScriptResponse response;
  private ScriptRequest request;
  private boolean HAS_RESULTS = false;

  public AnetaScript() {}

  public AnetaScript(ScriptRequest request) {
    this.request = request;
  }

  public void setResponse(ScriptResponse response) {
    if (HAS_RESULTS) {
      this.response = response;
    }
  }

  public ScriptResponse getResponse() {
    if (HAS_RESULTS) {
      return response;
    }
    return null;
  }

  public ScriptRequest getRequest() {
    return request;
  }

  public void setHasResults(boolean HAS_RESULTS) {
    this.HAS_RESULTS = HAS_RESULTS;
  }
}
