package com.example.dinote.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.base.BaseViewHolder;
import com.example.dinote.databinding.ItemHotTagBinding;
import com.example.dinote.model.Tag;
import com.example.dinote.utils.ColorUtils;
import com.example.dinote.utils.ReDesign;

import java.util.List;
import java.util.Random;

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
        ItemHotTagBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hot_tag, parent, false);
        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.bindData(tag);


    }

    @Override
    public int getItemCount() {
        if (tagList != null) {
            return tagList.size();
        } else {
            return 0;
        }
    }


    public class TagHolder extends BaseViewHolder<Tag> {
        private ItemHotTagBinding mBinding;

        public TagHolder(@NonNull ViewDataBinding itemView) {
            super(itemView);
            mBinding = (ItemHotTagBinding) itemView;
        }

        @Override
        public void bindData(Tag obj) {
            onResizeViews();
            onClickViews(obj);

            mBinding.tvItemHotTag.setText(obj.getContentTag());

            int random = new Random().nextInt(13);
            mBinding.cvItemHead.setCardBackgroundColor(ColorUtils.arrayColor()[random]);
            mBinding.tvItemHotTag.setTextColor(Color.WHITE);
        }

        @Override
        public void onResizeViews() {
            ReDesign.resizeImage(mBinding.imvItemHotTag, 48, 48);
        }

        @Override
        public void onClickViews(Tag o) {
            itemView.setOnClickListener(view -> {
                tagAdapterListener.onSendData(tagList.get(getLayoutPosition()));
            });
        }
    }

    public interface TagAdapterListener {
        void onSendData(Tag tag);
    }

}
