package com.example.dinote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.model.SearchHistory;

import java.util.List;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.HistorySearchViewHolder> {
    private List<SearchHistory> searchHistoryList;

    public HistorySearchAdapter(List<SearchHistory> searchHistoryList) {
        this.searchHistoryList = searchHistoryList;
    }

    @NonNull
    @Override
    public HistorySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false);
        return new HistorySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySearchViewHolder holder, int position) {
        SearchHistory searchHistory = searchHistoryList.get(position);
        holder.tvHistorySearch.setText(searchHistory.getContentSearch());
    }

    @Override
    public int getItemCount() {
        if (searchHistoryList != null) {
            return searchHistoryList.size();
        } else {
            return 0;
        }
    }

    public class HistorySearchViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHistorySearch;

        public HistorySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistorySearch = itemView.findViewById(R.id.tv_item_search_history);
            itemView.setOnClickListener(view -> historySearchAdapterListener.onSendData(searchHistoryList.get(getLayoutPosition())));
        }
    }

    private HistorySearchAdapterListener historySearchAdapterListener;

    public void setHistorySearchAdapterListener(HistorySearchAdapterListener historySearchAdapterListener) {
        this.historySearchAdapterListener = historySearchAdapterListener;
    }

    public interface HistorySearchAdapterListener {
        void onSendData(SearchHistory searchHistory);
    }
}
