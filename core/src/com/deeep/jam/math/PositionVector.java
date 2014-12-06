package com.deeep.jam.math;

/**
 * Created by Andreas on 10/18/2014.
 */
public class PositionVector {

    public float x, y, theta;

    public PositionVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PositionVector(float x, float y, float theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public PositionVector() {
    }

}

