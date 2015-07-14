package fr.tguerin.support.design.library.ui.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.model.Todo;
import io.realm.Realm;

import static fr.tguerin.support.design.library.data.TodoProvider.todoProvider;

public class EditTodoActivity extends AppCompatActivity {

    @Bind(R.id.todo_title) EditText titleView;
    @Bind(R.id.todo_content) EditText contentView;

    public static final String EXTRA_RESULT = "fr.tguerin.support.design.library.EXTRA_RESULT";
    private static final String EXTRA_TODO_ID = "fr.tguerin.support.design.library.EXTRA_TODO_ID";
    public static final int TODO_CREATED = 1;
    public static final int TODO_DELETED = 2;
    public static final int TODO_UPDATED = 3;

    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_todo_activity);
        ButterKnife.bind(this);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TODO_ID)) {
            todo = todoProvider().findOne(intent.getStringExtra(EXTRA_TODO_ID));
            titleView.setText(todo.getTitle());
            contentView.setText(todo.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            syncTodo();
            setResult(RESULT_OK, buildResultIntent());
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (itemId == R.id.action_delete_todo) {
            deleteTodo();
            setResult(RESULT_OK, buildResultIntent());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTodo() {
        todoProvider().delete(todo);
        todo = null;
    }

    @Override
    public void onBackPressed() {
        syncTodo();
        setResult(RESULT_OK, buildResultIntent());
        super.onBackPressed();
    }

    private void syncTodo() {
        boolean hasData = hasData();
        if (hasData) {
            final String title = titleView.getText().toString();
            final String content = contentView.getText().toString();
            if (todo == null || !todo.isValid()) {
                todo = todoProvider().save(new Todo(title, content));
            } else {
                todoProvider().execute(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        todo.setTitle(title);
                        todo.setContent(content);
                    }
                });
            }
        } else if (todo != null) {
            todoProvider().delete(todo);
        }
    }

    public boolean hasData() {
        return titleView.length() > 0 || contentView.length() > 0;
    }

    @NonNull
    private Intent buildResultIntent() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_RESULT, getAction());
        Intent intent = new Intent();
        intent.putExtras(bundle);
        return intent;
    }

    private int getAction() {
        int action = TODO_CREATED;
        if (todo == null) {
            action = TODO_DELETED;
        } else if (getIntent().hasExtra(EXTRA_TODO_ID)) {
            action = TODO_UPDATED;
        }
        return action;
    }

    public static void startForResult(Activity activity, int requestCode) {
        startForResult(activity, null, requestCode);
    }

    public static void startForResult(Activity activity, String todoId, int requestCode) {
        Intent intent = new Intent(activity, EditTodoActivity.class);
        if (todoId != null) {
            intent.putExtra(EXTRA_TODO_ID, todoId);
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
