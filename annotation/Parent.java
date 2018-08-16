package com.lkp.java.annotation;

/**
 * Created by liukepeng on 2018/8/15.
 */
@InheritedTest("InheritedTest：使用@Inherited的class")
@NoInheritedTest("NoInheritedTest：未使用@Inherited的class")
public class Parent {

    @InheritedTest("InheritedTest：使用@Inherited method")
    @NoInheritedTest("NoInheritedTest：未使用@Inherited method")
    public void method() {}

    @InheritedTest("InheritedTest：使用@Inherited method2")
    @NoInheritedTest("NoInheritedTest：未使用@Inherited method2")
    public void method2() {}

    @InheritedTest("InheritedTest：使用@Inherited field")
    @NoInheritedTest("NoInheritedTest：未使用@Inherited field")
    public String a;


}
