package fr.tguerin.support.design.library.ui.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.model.Todo;

public class TodoItemView extends CardView {

    @Bind(R.id.todo_title) TextView titleView;
    @Bind(R.id.todo_content) TextView contentView;

    private Todo todo;

    public TodoItemView(Context context) {
        super(context);
    }

    public TodoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bind(Todo todo) {
        this.todo = todo;
        titleView.setText(todo.getTitle());
        contentView.setText(todo.getContent());
    }

    public Todo getTodo() {
        return todo;
    }
}
