package com.example.dinote.model;

import java.io.Serializable;

public class Motion implements Serializable {
    private int id;
    private int imgMotion;
    private int motion;

    public Motion(int id, int imgMotion, int motion) {
        this.id = id;
        this.imgMotion = imgMotion;
        this.motion = motion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgMotion() {
        return imgMotion;
    }

    public void setImgMotion(int imgMotion) {
        this.imgMotion = imgMotion;
    }

    public int getMotion() {
        return motion;
    }

    public void setMotion(int motion) {
        this.motion = motion;
    }
}
