package com.aneta.core.scripting.models;

import java.io.Reader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ScriptRequest implements Serializable {
  private Reader script;
  private Set<ScriptVariable<?>> variables = new HashSet<>();

  public ScriptRequest() {}

  public ScriptRequest(Reader script) {
    this.script = script;
  }

  public ScriptRequest(Reader script, Set<ScriptVariable<?>> variables) {
    this.script = script;

    for (ScriptVariable<?> variable: variables) {
      addVariable(variable);
    }
  }

  public void addVariable(ScriptVariable<?> variable) {
    if (!(variables.contains(variable))) {
      variables.add(variable);
    }
  }

  public Set<ScriptVariable<?>> getVariables() {
    return variables;
  }

  public void setScript(Reader script) {
    this.script = script;
  }

  public Reader getScript() {
    return script;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ScriptRequest)) return false;
    ScriptRequest that = (ScriptRequest) o;
    return Objects.equals(script, that.script) &&
            Objects.equals(getVariables(), that.getVariables());
  }

  @Override
  public int hashCode() {
    return Objects.hash(script, getVariables());
  }
}
