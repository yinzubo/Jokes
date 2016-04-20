package com.bitech.joke;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * <p>activity的生命周期</p>
 * Created on 2016/4/8 11:05.
 *
 * @author Lucy
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
