package com.aneta.services.scripting.model.types;

public enum ScriptType {
  ON_DEMAND,
  ON_EVENT,
  ON_CREATED,
  ON_UPDATED,
  ON_DELETED,
  BEFORE_CREATED,
  BEFORE_UPDATED,
  BEFORE_DELETED,
  AFTER_CREATED,
  AFTER_UPDATED,
  AFTER_DELETED;
}
