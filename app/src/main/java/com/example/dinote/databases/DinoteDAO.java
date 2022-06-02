package com.example.dinote.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dinote.model.Dinote;

import java.util.List;

@Dao
public interface DinoteDAO {


    @Query("select * from dinote order by date desc limit :limit offset :next ")
    List<Dinote> getAllDinote(int limit, int next);


    @Query("select * from dinote where isLike = 1 order by date desc ")
    List<Dinote> getAllDinoteFavorite();

    @Insert
    void insertDinote(Dinote dinote);

    @Update
    void updateDinote(Dinote dinote);

    @Delete
    void deleteDinote(Dinote dinote);

    @Query("select * from dinote where title like '%' ||:search||'%' ")
    List<Dinote> searchList(String search);

    @Query("select COUNT(id) from dinote")
    int getTotalItemCount();

    @Query("select * from dinote where title like '%' ||:search || '%' or content like '%' || :search || '%' or tagList like '%' || :search || '%' order by date limit :limit offset :next ")
    List<Dinote> searchAll(String search, int limit , int next);

    @Query("select count(id) from dinote where title like '%' ||:search || '%' or content like '%' || :search || '%' or tagList like '%' || :search || '%' ")
   int getTotalSearch (String search);

}
