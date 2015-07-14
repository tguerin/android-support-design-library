package fr.tguerin.support.design.library;

import android.app.Application;

import io.realm.Realm;

public class TodoApplication extends Application {

    private static TodoApplication todoApplication;

    private Realm realm;

    public TodoApplication() {
        todoApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        realm = Realm.getInstance(this);
        realm.setAutoRefresh(true);
    }

    public static Realm realm() {
        return todoApplication.realm;
    }

    public static TodoApplication get() {
        return todoApplication;
    }
}
