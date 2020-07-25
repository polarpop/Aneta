package com.aneta.services.scripting.aggregate;

import com.aneta.services.scripting.command.RunScriptCommand;
import com.aneta.services.scripting.command.creation.CreateScriptCommand;
import com.aneta.services.scripting.entity.SysScript;
import com.aneta.services.scripting.event.RunScriptEvent;
import com.aneta.services.scripting.event.creation.CreateScriptEvent;
import com.aneta.services.scripting.event.creation.ScriptCreatedEvent;
import com.aneta.services.scripting.event.exception.ScriptCreationErrorEvent;
import com.aneta.services.scripting.repository.ScriptRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;
import java.util.UUID;

@Aggregate
public class ScriptAggregate {

  @Autowired
  private ScriptRepository repository;

  @AggregateIdentifier
  private UUID id;

  public ScriptAggregate() {}

  @CommandHandler
  public ScriptAggregate(CreateScriptCommand createScriptCommand) {
    AggregateLifecycle.apply(new CreateScriptEvent(
            createScriptCommand.getSysScript(),
            createScriptCommand.getType()
    ));
  }

  @EventSourcingHandler
  protected void on(CreateScriptEvent created) {
    SysScript sysScript = created.getSysScript();
    try {
      this.repository.save(sysScript);
      AggregateLifecycle.apply(new ScriptCreatedEvent(
              sysScript.getId()
      ));
    } catch (NonUniqueResultException e) {
      AggregateLifecycle.apply(new ScriptCreationErrorEvent(
              sysScript.getId(),
              "A script with that label already exists"
      ));
    } catch (Exception e) {
      AggregateLifecycle.apply(new ScriptCreationErrorEvent(
              sysScript.getId(),
              e.getMessage()
      ));
    }
  }

  @CommandHandler
  protected void on(RunScriptCommand cmd) {
    Optional<SysScript> script = this.repository.findById(cmd.id);
    script.ifPresent(sysScript -> AggregateLifecycle.apply(new RunScriptEvent(sysScript)));
  }
}
