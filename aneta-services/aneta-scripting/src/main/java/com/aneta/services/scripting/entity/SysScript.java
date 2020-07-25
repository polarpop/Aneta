package com.aneta.services.scripting.entity;

import com.aneta.services.scripting.model.types.ScriptType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_script")
public class SysScript extends BaseEntity {

  @Column(name = "script_label")
  private String label;

  @Column(name = "script_api_name", unique = true)
  private String name;

  @Column(name = "script_type")
  private ScriptType scriptType = ScriptType.ON_DEMAND;

  public SysScript() {
    super();
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
    setName(label);
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name.toLowerCase().replaceAll("[ ]+", "_");
  }

  public ScriptType getScriptType() {
    return scriptType;
  }

  public void setScriptType(ScriptType scriptType) {
    this.scriptType = scriptType;
  }
}
