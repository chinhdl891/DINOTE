package com.example.dinote.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "dinote")
public class Dinote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private long date;
    @ColumnInfo
    private String content;
    @ColumnInfo
    private String motion;
    @ColumnInfo
    private List<Tag> tagList;

    public Dinote(int id, long date, String content, String motion, List<Tag> tagList) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.motion = motion;
        this.tagList = tagList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }
}
