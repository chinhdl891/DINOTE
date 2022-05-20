package com.example.dinote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.model.Dinote;
import com.example.dinote.utils.ReDesign;

import java.util.Calendar;
import java.util.List;

public class DinoteAdapter extends RecyclerView.Adapter<DinoteAdapter.DinoteViewHolder> {
    List<Dinote> dinoteList;

    public void setDinoteList(List<Dinote> dinoteList) {
        this.dinoteList = dinoteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DinoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinote, parent, false);
        return new DinoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DinoteViewHolder holder, int position) {

        Dinote dinote = dinoteList.get(position);
        holder.tvDinoteContent.setText(dinote.getContent());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dinote.getDate());

        int moth = calendar.get(Calendar.MONTH) +1;
        holder.tvDinoteMoth.setText("Th" + moth);
        holder.tvDinoteYear.setText(calendar.get(Calendar.YEAR) + "");
        holder.tvDinoteDay.setText(calendar.get(Calendar.DAY_OF_MONTH) +"");
        holder.tvDinoteContent.setText(dinote.getContent());
        holder.tvDinoteTitle.setText(dinote.getTitle());


    }

    @Override
    public int getItemCount() {
        if (dinoteList == null) {
            return 0;
        } else {
            return dinoteList.size();
        }
    }

    public class DinoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDinoteDay, tvDinoteMoth, tvDinoteYear;
        private TextView tvDinoteTitle, tvDinoteContent;

        public DinoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDinoteTitle = itemView.findViewById(R.id.tv_item_dinote_title);
            tvDinoteDay = itemView.findViewById(R.id.tv_item_dinote_day);
            tvDinoteYear = itemView.findViewById(R.id.tv_item_dinote_year);
            tvDinoteMoth = itemView.findViewById(R.id.tv_item_dinote_moth);
            tvDinoteContent = itemView.findViewById(R.id.tv_item_dinote_content);
            ReDesign.resizeImage(itemView.findViewById(R.id.imv_item_dinote_bg),164,164);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (dinoteAdapterListener != null) {
                       dinoteAdapterListener.onGotoDetailDinote(dinoteList.get(getLayoutPosition()));
                   }
                }
            });


        }


    }
    private DinoteAdapterListener dinoteAdapterListener;

    public void setDinoteAdapterListener(DinoteAdapterListener dinoteAdapterListener) {
        this.dinoteAdapterListener = dinoteAdapterListener;
    }

    public interface DinoteAdapterListener{

        void onGotoDetailDinote(Dinote dinote);

    }
}
