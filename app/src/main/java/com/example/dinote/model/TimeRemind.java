package com.example.dinote.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class TimeRemind {
    @PrimaryKey()
    private long id;
    @ColumnInfo
    private long time;
    @ColumnInfo
    private int status;

    public TimeRemind(long id, long time, int status) {
        this.id = id;
        this.time = time;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
