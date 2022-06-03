package com.example.dinote.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String contentSearch;

    public SearchHistory(String contentSearch) {
        this.contentSearch = contentSearch;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContentSearch() {
        return contentSearch;
    }

    public void setContentSearch(String contentSearch) {
        this.contentSearch = contentSearch;
    }
}
