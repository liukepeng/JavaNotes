package com.lkp.java.annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liukepeng on 2018/8/15.
 */
public class Test {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Class<Child> childClass = Child.class;
        //对类测试
        System.out.println("-------------------------------------");
        System.out.println("对类测试");
        if (childClass.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(childClass.getAnnotation(InheritedTest.class).value());
        }

        if (childClass.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(childClass.getAnnotation(NoInheritedTest.class).value());
        }

        //对方法1测试
        System.out.println("-------------------------------------");
        System.out.println("对方法1测试");
        Method method = childClass.getMethod("method", null);
        if (method.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(method.getAnnotation(InheritedTest.class).value());
        }

        if (method.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(method.getAnnotation(NoInheritedTest.class).value());
        }

        //对方法2测试
        System.out.println("-------------------------------------");
        System.out.println("对方法2测试");
        Method method2 = childClass.getMethod("method2");
        if (method2.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(method2.getAnnotation(InheritedTest.class).value());
        }

        if (method2.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(method2.getAnnotation(NoInheritedTest.class).value());
        }

        //对变量测试
        System.out.println("-------------------------------------");
        System.out.println("对变量测试");
        Field field = childClass.getField("a");
        if (field.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(field.getAnnotation(InheritedTest.class).value());
        }

        if (field.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(field.getAnnotation(NoInheritedTest.class).value());
        }
    }
}
