package com.example.pinyintest;

import io.goeasy.GoEasy;


/**
 * goEasy使用案例
 * 必须还另外包括gson和slf4j-api两个jar包
 */
public class GoeasyTest {
    public static void main(String[] args) {
        GoEasy goEasy = new GoEasy("BC-c9279786f4974c33a8aba98596570789");
        goEasy.publish("test","你好");
    }
}
