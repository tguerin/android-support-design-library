package fr.tguerin.support.design.library.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Todo extends RealmObject {


    @PrimaryKey
    private String id;
    private String title;
    private String content;

    public Todo() {

    }

    public Todo(String title, String content) {
        this.id = nextPrimaryKey();
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static String nextPrimaryKey() {
        return UUID.randomUUID().toString();
    }
}
