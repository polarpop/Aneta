package com.aneta.services.scripting.repository;

import com.aneta.services.scripting.entity.SysScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScriptRepository extends JpaRepository<SysScript, UUID> {
}
