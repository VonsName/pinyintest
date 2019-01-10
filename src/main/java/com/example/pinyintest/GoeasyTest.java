package com.example.pinyintest;

import java.net.URL;


/**
 * goEasy使用案例
 * 必须还另外包括gson和slf4j-api两个jar包
 */
public class GoeasyTest {
    public static void main(String[] args) {
//        GoEasy goEasy = new GoEasy("BC-key");
//        goEasy.publish("go2","你好");

        Class<GoeasyTest> clazz = GoeasyTest.class;
        String simpleName = clazz.getSimpleName();
        System.out.println(simpleName);
        URL classpath = clazz.getResource(simpleName + ".class");
        System.out.println(classpath.toString());

        System.out.println("aa".hashCode());
        char a = 'a';
    }
}
