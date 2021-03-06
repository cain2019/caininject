package com.core.inject;


import android.util.Log;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解的 注解
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectEvent {

    //事件三要素

//     btn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Log.e(TAG,"setOnClickListener");
//        }
//    });


    String listenerSetter();

    Class listenerType();

    String callBackMethod();

}
