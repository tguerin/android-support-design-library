package fr.tguerin.support.design.library.ui.list;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.model.Todo;
import fr.tguerin.support.design.library.ui.util.EmptyRecyclerView;
import fr.tguerin.support.design.library.ui.util.ItemViewHolder;

public class TodoListView extends CoordinatorLayout {

    @Bind(R.id.todo_list) EmptyRecyclerView todoListView;
    @Bind(R.id.todo_empty) View emptyView;

    public TodoListView(Context context) {
        super(context);
    }

    public TodoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodoListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        todoListView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    public void bind(List<Todo> todos, TodoListAdapter.TodoClickedListener todoClickedListener) {
        todoListView.setAdapter(new TodoListAdapter(getContext(), todoClickedListener, todos));
        todoListView.setEmptyView(emptyView);
    }

    public static class TodoListAdapter extends RecyclerView.Adapter<ItemViewHolder<TodoItemView>> implements OnClickListener {

        private final List<Todo> todos;
        private final LayoutInflater inflater;
        private final TodoClickedListener listener;

        public TodoListAdapter(Context context, TodoClickedListener listener, List<Todo> todos) {
            this.todos = todos;
            this.inflater = LayoutInflater.from(context);
            this.listener = listener;
        }

        @Override
        public ItemViewHolder<TodoItemView> onCreateViewHolder(ViewGroup parent, int viewType) {
            TodoItemView todoItemView = (TodoItemView) inflater.inflate(R.layout.todo_item_view, parent, false);
            todoItemView.setOnClickListener(this);
            return new ItemViewHolder<>(todoItemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder<TodoItemView> holder, int position) {
            holder.getItemView().bind(todos.get(getItemCount() - position - 1));
        }

        @Override
        public int getItemCount() {
            return todos.size();
        }

        @Override
        public void onClick(View v) {
            listener.onTodoClicked(((TodoItemView) v).getTodo());
        }

        public interface TodoClickedListener {
            void onTodoClicked(Todo todo);
        }
    }
}
