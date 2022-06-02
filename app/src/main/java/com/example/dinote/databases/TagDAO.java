package com.example.dinote.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dinote.model.Tag;

import java.util.List;

@Dao
public interface TagDAO {
    @Query("select * from tag")
    List<Tag> getAllTag();

    @Insert
    void insertTag(Tag tag);

    @Query("SELECT COUNT(contentTag) FROM tag where contentTag = :tag")
    int getCount(String tag);

    @Query("select * from tag order by id desc limit 10 ")
    List<Tag> listHotTag();

    @Query("select * from tag where contentTag = :tag ")
    List<Tag> getTagFindByTagConTent(String tag);
}
