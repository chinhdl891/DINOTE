package com.example.dinote.databasedinote;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dinote.model.Dinote;

import java.util.List;

@Dao
public interface DinoteDAO {


    @Query("select * from dinote")
    List<Dinote> getAllDinote();

    @Insert
    void insertDinote(Dinote dinote);

    @Update
    void updateDinote(Dinote dinote);

    @Delete
    void deleteDinote(Dinote dinote);

    @Query("select * from dinote where content like '%' +:search+'%' ")
    List<Dinote> searchList(String search);

}
