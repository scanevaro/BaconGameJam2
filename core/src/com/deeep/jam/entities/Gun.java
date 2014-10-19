package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Elmar on 18-10-2014.
 */
public abstract class Gun {
    protected int level = 0;
    protected boolean damaged = false;
    public float rotation;
    public float x, y, offX, offY;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public float bulletTimer = 0;
    public final float bulletSpeed = 0.5f;
    public float theta;

    public Gun() {

    }



    public abstract void update(Ship ship, float deltaT);

    public abstract void render(SpriteBatch spriteBatch);

}