package com.aneta.services.scripting.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class BaseCommand<ID> {

  @TargetAggregateIdentifier
  public ID id;

  public BaseCommand(ID id) {
    this.id = id;
  }
}
