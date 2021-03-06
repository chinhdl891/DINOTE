package com.example.dinote.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "dinote")
public class Dinote implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private long date;
    @ColumnInfo
    private String content;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private int motion;
    @ColumnInfo
    private String imageUri;
    @ColumnInfo
    private String imageDes;

    @ColumnInfo
    private int isLike;

    @ColumnInfo
    private List<Tag> tagList;

    public Dinote(int id, long date, String content, String title, int motion, String imageUri, String imageDes, int isLike, List<Tag> tagList) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.title = title;
        this.motion = motion;
        this.imageUri = imageUri;
        this.imageDes = imageDes;
        this.isLike = isLike;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMotion() {
        return motion;
    }

    public void setMotion(int motion) {
        this.motion = motion;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageDes() {
        return imageDes;
    }

    public void setImageDes(String imageDes) {
        this.imageDes = imageDes;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}

