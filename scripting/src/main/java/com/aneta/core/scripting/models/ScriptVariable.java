package com.aneta.core.scripting.models;

import java.io.Serializable;
import java.util.Objects;

public class ScriptVariable<T> implements Serializable {
  private String name;
  private T value;

  public ScriptVariable(String name, T value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public T getValue() {
    return value;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ScriptVariable)) return false;
    ScriptVariable<?> that = (ScriptVariable<?>) o;
    return getName().equals(that.getName()) &&
            getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getValue());
  }
}
