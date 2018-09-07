package com.example.pinyintest;

import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeSet;

public class SetTest {
    public static void main(String[] args) {
        Set<Integer> set=new HashSet<>();
        set.add(5);
        set.add(3);
        set.add(1);
        for (Integer u:set
             ) {
            System.out.println(u);
        }
        System.out.println();
        Set<Integer> set1=new TreeSet<>();
        set1.add(3);
        set1.add(2);
        set1.add(5);
        for (Integer u:set1
                ) {
            System.out.println(u);
        }
        Spliterator<Integer> spliterator = set.spliterator();
        System.out.println(spliterator);
    }
}
