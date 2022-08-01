package com.example;

import com.anotherpackage.MyTestClass;
import org.junit.Test;

public class MySampleTest {

    MyTestClass myTestClass = new MyTestClass();

    @Test
    public void testSample(){
        System.out.print("Hello World");
    }
}
