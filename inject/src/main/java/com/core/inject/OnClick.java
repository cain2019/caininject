package com.core.inject;


import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@InjectEvent(listenerSetter = "setOnClickListener", listenerType = View.OnClickListener.class, callBackMethod = "onClick")
public @interface OnClick {

    // 一个方法上 有多个注解
    int[] value();
}
