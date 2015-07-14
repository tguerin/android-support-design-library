package fr.tguerin.support.design.library.data;

import java.util.List;

import fr.tguerin.support.design.library.model.Todo;
import io.realm.Realm;

import static fr.tguerin.support.design.library.TodoApplication.realm;

public class TodoProvider {

    private static TodoProvider INSTANCE = new TodoProvider();

    public Todo save(final Todo todo) {
        Realm realm = realm();
        realm.beginTransaction();
        Todo savedTodo = realm.copyToRealm(todo);
        realm.commitTransaction();
        return savedTodo;
    }

    public void delete(final Todo todo) {
        if (todo == null) {
            return;
        }
        realm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                todo.removeFromRealm();
            }
        });

    }

    public List<Todo> all() {
        return realm().where(Todo.class).findAll();
    }

    public Todo findOne(String todoId) {
        return realm().where(Todo.class).equalTo("id", todoId).findFirst();
    }

    public static TodoProvider todoProvider() {
        return INSTANCE;
    }

    public void execute(Realm.Transaction transaction) {
        realm().executeTransaction(transaction);
    }
}
