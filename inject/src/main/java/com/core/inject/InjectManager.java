package com.core.inject;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {


    public static void inject(Object obj) {
        injectLayout(obj);

        injectValue(obj);

        injectEvent(obj);
    }

    /**
     * 事件注入 点击 双击等 方法上的注解
     * @param obj
     */
    private static void injectEvent(final Object obj) {
        Class<?> aClass = obj.getClass();

        //取得方法 的 数组
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (final Method method : declaredMethods) {

            // 方法上 有 多个注解
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();

            for (Annotation annotation : declaredAnnotations) {

                //获取注解 的 注解
                Class<? extends Annotation> annotationType = annotation.annotationType();
                InjectEvent injectEvent = annotationType.getAnnotation(InjectEvent.class);

                // 三要素
                String listenerSetter = injectEvent.listenerSetter();
                Class listenerType = injectEvent.listenerType();
                String callBackMethod = injectEvent.callBackMethod();

                ListenerHandle listenerHandle = new ListenerHandle(method, obj);

                Object listerProxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, listenerHandle);


                try {
                    Method method1 = annotationType.getDeclaredMethod("value");
                    int[] ids = (int[]) method1.invoke(annotation);

                    for (int id : ids) {
                        // 获取 id
                        Method findViewById = aClass.getMethod("findViewById", int.class);
                        View btn = (View) findViewById.invoke(obj, id);

                        Method setterMethod = btn.getClass().getMethod(listenerSetter, listenerType);

                        setterMethod.invoke(btn,listerProxy);
                    }

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * 属性注入 findViewById
     *
     * @param obj
     */
    private static void injectValue(Object obj) {

        Class<?> aClass = obj.getClass();

        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field field : declaredFields) {

            InjectView annotation = field.getAnnotation(InjectView.class);

            if (annotation != null) {
                int value = annotation.value();

                try {
                    Method findViewById = aClass.getMethod("findViewById", int.class);

                    View view = (View) findViewById.invoke(obj, value);

                    field.setAccessible(true);

                    field.set(obj,view);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }


        }

    }


    /**
     * setContentView(R.layout.activity_main);
     * 通过注解 xml页面跟activiyt 绑定 ioc 控制反转 也叫依赖注入
     *
     * @param obj
     */
    private static void injectLayout(Object obj) {

        //反射得到类
        Class<?> clazz = obj.getClass();
        //得到注解
        InjectLayout annotation = clazz.getAnnotation(InjectLayout.class);

        if (annotation != null) {
            int value = annotation.value();

            try {
                //得到方法
                Method setContentView = clazz.getMethod("setContentView", int.class);

                setContentView.invoke(obj, value);


            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


    }


}
