package com.aneta.core.scripting.engine.annotations;

import com.aneta.core.scripting.engine.type.LanguageType;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Language {
}
