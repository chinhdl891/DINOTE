package com.example.dinote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.model.Dinote;

import java.util.List;

public class DinoteAdapter extends RecyclerView.Adapter<DinoteAdapter.DinoteViewHolder> {
    List<Dinote> dinoteList;

    public DinoteAdapter(List<Dinote> dinoteList) {
        this.dinoteList = dinoteList;
    }

    @NonNull
    @Override
    public DinoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinote, parent,false);
        return new DinoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DinoteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dinoteList.size();
    }

    public class DinoteViewHolder extends RecyclerView.ViewHolder{
    private TextView tvDinoteDay,tvDinoteMoth,tvDinoteYear;
    private TextView tvDinoteTitle, tvDinoteContent;
        public DinoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDinoteTitle = itemView.findViewById(R.id.tv_item_dinote_title);
            tvDinoteDay = itemView.findViewById(R.id.tv_item_dinote_day);
            tvDinoteYear = itemView.findViewById(R.id.tv_item_dinote_year);
            tvDinoteMoth = itemView.findViewById(R.id.tv_item_dinote_moth);
            tvDinoteContent = itemView.findViewById(R.id.tv_item_dinote_content);
        }
    }
}
