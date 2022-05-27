package com.example.dinote.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dinote.model.SearchHistory;

import java.util.List;

@Dao
public interface  SearchDAO {
    @Query("select * from searchhistory limit 20")
    List<SearchHistory> getListStory();

    @Insert
    void insert(SearchHistory searchHistory);

    @Query("delete from SearchHistory")
    void delete();
}
