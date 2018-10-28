package com.yeoga.realmmemo;


import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class dbModel extends RealmObject {

    private String id = UUID.randomUUID().toString();
    private Date createdAt = new Date();
    String text;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}
