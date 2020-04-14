package xyz.rthqks.signiphy.inject;

import javax.inject.Scope;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

@Retention(AnnotationRetention.RUNTIME)
@Scope
@interface ActivityScope {}
