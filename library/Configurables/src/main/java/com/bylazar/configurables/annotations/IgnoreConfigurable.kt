package com.bylazar.configurables.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class IgnoreConfigurable
