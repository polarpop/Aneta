package com.aneta.core.scripting.models;

import java.io.Serializable;
import java.util.Objects;

public class ScriptResponse<Results> implements Serializable {
  private Results results;
  private Object error = null;

  public ScriptResponse() {}

  public ScriptResponse(Results results) {
    this.results = results;
  }

  public Results getResults() {
    return results;
  }

  public void setError(Object error) {
    this.error = error;
  }

  public Object getError() {
    return error;
  }

  public boolean hasErrors() {
    return error != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ScriptResponse)) return false;
    ScriptResponse<?> that = (ScriptResponse<?>) o;
    return Objects.equals(getResults(), that.getResults()) &&
            Objects.equals(getError(), that.getError());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getResults(), getError());
  }
}
