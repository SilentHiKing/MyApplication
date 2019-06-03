package com.h.myapplication.test;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    int icon;
    String name;

    public Book(String name, int icon) {
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

    @Override
    public String toString() {
        return "name:" + name + "icon" + icon;
    }
}
