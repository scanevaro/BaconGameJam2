package com.deeep.jam;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Bullet {
    public final float speed = 500f;
    public float x, y;
    public float rotation;
    public float lifeTime = 5;
    public boolean alive = true;

    public Bullet(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public void update(float deltaT) {
        x += Math.cos(rotation) * speed * deltaT;
        y += Math.sin(rotation) * speed * deltaT;
        lifeTime -= deltaT;
        if (lifeTime <= 0) {
            alive = false;
        }
    }
}
