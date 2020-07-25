package com.aneta.core.scripting.engine.type;

public enum LanguageType {
  JavaScript("JavaScript"),
  Python("Jython"),
  Kotlin("Kotlin"),
  Ruby("JRuby"),
  Groovy("Groovy");

  private LanguageType(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }
}
