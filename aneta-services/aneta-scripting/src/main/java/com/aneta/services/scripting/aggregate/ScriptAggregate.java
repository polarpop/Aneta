package com.aneta.services.scripting.aggregate;

import com.aneta.core.scripting.engine.model.ScriptingConstructorArgs;
import com.aneta.services.scripting.command.RunScriptCommand;
import com.aneta.services.scripting.command.creation.CreateScriptCommand;
import com.aneta.services.scripting.entity.SysScript;
import com.aneta.services.scripting.event.RunScriptEvent;
import com.aneta.services.scripting.event.creation.CreateScriptEvent;
import com.aneta.services.scripting.event.creation.ScriptCreatedEvent;
import com.aneta.services.scripting.event.exception.RunScriptNotFoundEvent;
import com.aneta.services.scripting.event.exception.ScriptCreationErrorEvent;
import com.aneta.services.scripting.repository.ScriptRepository;
import com.aneta.services.scripting.service.ScriptingEngineService;
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

  @AggregateIdentifier
  private UUID id;

  @Autowired
  private ScriptRepository repository;

  private ScriptingEngineService scriptingEngineService;

  public ScriptAggregate() {}

  public ScriptAggregate(ScriptingConstructorArgs args) {
    this.scriptingEngineService = new ScriptingEngineService(args);
  }

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
    script.ifPresentOrElse(sysScript -> AggregateLifecycle.apply(new RunScriptEvent(sysScript))
            , () -> AggregateLifecycle.apply(new RunScriptNotFoundEvent(cmd.id)));
  }

  @EventSourcingHandler
  protected void on(RunScriptEvent evt) {
    try {
      scriptingEngineService.engine.setScript(evt.getScript().getLabel());
      scriptingEngineService.engine.run();
    } catch (Exception e) {
      AggregateLifecycle.apply(new RunScriptNotFoundEvent(evt.getId()));
    }
  }

  @EventSourcingHandler
  protected void on(RunScriptNotFoundEvent evt) {

  }
}
