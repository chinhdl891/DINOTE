package com.example.dinote.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dinote.model.TimeRemind;

import java.util.List;

@Dao
public interface TimeRemindDAO {
    @Insert
    void insertTime(TimeRemind timeRemind);
    @Update
    void update(TimeRemind timeRemind);
    @Delete
    void delete(TimeRemind timeRemind);
    @Query("select * from timeremind")
    List<TimeRemind> getListTimeRemind();
    @Query("SELECT COUNT(time) FROM TimeRemind where time = :time")
    int getCountTimeAlarm(long time);

}
