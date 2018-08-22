package com.example.pinyintest;

public class Hobby {
    private Integer id;
    private String name;


    public Hobby(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hobby() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
