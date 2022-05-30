package com.example.dinote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.model.Tag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagHolder> {
    private List<Tag> tagList;
    private TagAdapterListener tagAdapterListener;

    public TagAdapter(List<Tag> tagList, TagAdapterListener tagAdapterListener) {
        this.tagList = tagList;
        this.tagAdapterListener = tagAdapterListener;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false);
        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.tvTagName.setText(tag.getContentTag());
    }

    @Override
    public int getItemCount() {
        if (tagList != null) {
            return tagList.size();
        } else {
            return 0;
        }
    }


    public class TagHolder extends RecyclerView.ViewHolder {
        private TextView tvTagName;

        public TagHolder(@NonNull View itemView) {
            super(itemView);
            tvTagName = itemView.findViewById(R.id.tv_item_search_history);
            itemView.setOnClickListener(view -> tagAdapterListener.onSendData(tagList.get(getLayoutPosition())));
        }
    }

    public interface TagAdapterListener {
        void onSendData(Tag tag);
    }

}
