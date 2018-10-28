package com.yeoga.realmmemo;


import io.realm.RealmObject;

public class dbModel extends RealmObject {

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}
