package com.example.dinote.model;

public class Motion {
    private int id;
    private int imgMotion;
    private String motion;

    public Motion(int id, int imgMotion, String motion) {
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

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }
}
