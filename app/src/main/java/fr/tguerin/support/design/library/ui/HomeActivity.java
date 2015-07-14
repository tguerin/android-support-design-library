package fr.tguerin.support.design.library.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.OnClick;
import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.model.Todo;
import fr.tguerin.support.design.library.ui.edit.EditTodoActivity;
import fr.tguerin.support.design.library.ui.list.TodoListView;
import fr.tguerin.support.design.library.ui.util.DrawerActivity;

import static fr.tguerin.support.design.library.data.TodoProvider.todoProvider;
import static fr.tguerin.support.design.library.ui.list.TodoListView.TodoListAdapter.TodoClickedListener;

public class HomeActivity extends DrawerActivity {

    public static final int EDIT_TODO_REQUEST_CODE = 1001;

    public HomeActivity() {
        super(R.layout.todo_list_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((TodoListView) containerView).bind(todoProvider().all(), new TodoClickedListener() {
            @Override
            public void onTodoClicked(Todo todo) {
                EditTodoActivity.startForResult(HomeActivity.this, todo.getId(), EDIT_TODO_REQUEST_CODE);
            }
        });
    }

    @OnClick(R.id.new_todo)
    public void onNewTodoClicked() {
        EditTodoActivity.startForResult(this, EDIT_TODO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TODO_REQUEST_CODE) {
            switch (data.getIntExtra(EditTodoActivity.EXTRA_RESULT, -1)) {
                case EditTodoActivity.TODO_DELETED:
                    Toast.makeText(this, R.string.todo_deleted, Toast.LENGTH_SHORT).show();
                    break;
                case EditTodoActivity.TODO_UPDATED:
                    Toast.makeText(this, R.string.todo_updated, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, R.string.todo_created, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public int getSelfNavDrawerItem() {
        return R.id.my_todos;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
