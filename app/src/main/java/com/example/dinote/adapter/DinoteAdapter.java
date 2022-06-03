package com.example.dinote.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.base.BaseViewHolder;
import com.example.dinote.databinding.ItemDinoteBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.utils.ReDesign;

import java.util.Calendar;
import java.util.List;

public class DinoteAdapter extends RecyclerView.Adapter<DinoteAdapter.DinoteViewHolder> {
    private List<Dinote> dinoteList;


    public DinoteAdapter(List<Dinote> dinoteList) {
        this.dinoteList = dinoteList;
    }

    @NonNull
    @Override
    public DinoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDinoteBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_dinote, parent, false);
        return new DinoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DinoteViewHolder holder, int position) {

        Dinote dinote = dinoteList.get(position);
        holder.bindData(dinote);
    }

    @Override
    public int getItemCount() {
        if (dinoteList == null) {
            return 0;
        } else {
            return dinoteList.size();
        }
    }

    public class DinoteViewHolder extends BaseViewHolder<Dinote> {
        private ItemDinoteBinding mBinding;

        public DinoteViewHolder(@NonNull ViewDataBinding itemView) {
            super(itemView);
            mBinding = (ItemDinoteBinding) itemView;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void bindData(Dinote obj) {
            onResizeViews();
            onClickViews(obj);
            mBinding.tvItemDinoteContent.setText(obj.getContent());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(obj.getDate());
            int moth = calendar.get(Calendar.MONTH) + 1;

            mBinding.tvItemDinoteMoth.setText(String.format("Th %d",moth));
            mBinding.tvItemDinoteYear.setText(calendar.get(Calendar.YEAR) + "");
            mBinding.tvItemDinoteDay.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
            mBinding.tvItemDinoteContent.setText(obj.getContent());
            mBinding.tvItemDinoteTitle.setText(obj.getTitle());

        }

        @Override
        public void onResizeViews() {
            ReDesign.resizeImage(itemView.findViewById(R.id.imv_item_dinote_bg), 164, 164);
        }

        @Override
        public void onClickViews(Dinote o) {
            itemView.setOnClickListener(view -> {
                if (dinoteAdapterListener != null) {
                    dinoteAdapterListener.onGotoDetailDinote(dinoteList.get(getLayoutPosition()));
                }
            });
        }
    }

    public DinoteAdapterListener dinoteAdapterListener;

    public void setDinoteAdapterListener(DinoteAdapterListener dinoteAdapterListener) {
        this.dinoteAdapterListener = dinoteAdapterListener;
    }

    public interface DinoteAdapterListener {
        void onGotoDetailDinote(Dinote dinote);

    }
}
