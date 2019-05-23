package com.example.h.myapplication.test;

public class Book {

    int icon;
    String name;

    public Book( String name,int icon) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {

        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
