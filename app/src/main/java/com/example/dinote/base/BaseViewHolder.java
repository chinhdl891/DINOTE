package com.example.dinote.base;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull ViewDataBinding itemView) {
        super(itemView.getRoot());
    }

    public abstract void bindData(T obj);

    public abstract void onResizeViews();

    public abstract void onClickViews(T o);

}
