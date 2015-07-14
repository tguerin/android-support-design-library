package fr.tguerin.support.design.library.ui.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemViewHolder<T extends View> extends RecyclerView.ViewHolder {

    private T itemView;

    public ItemViewHolder(T itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public T getItemView() {
        return itemView;
    }
}
