package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Shaking;

import java.util.ArrayList;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Gun {

    public float rotation;
    public float x, y;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public float bulletTimer = 0;
    public final float bulletSpeed = 0.5f;

    public void update(Ship ship, float deltaT) {
        this.x = ship.x;
        this.y = ship.y;
        float deltaX = Gdx.input.getX() - x;
        float deltaY = Gdx.graphics.getHeight() - Gdx.input.getY() - y;
        rotation = (float) Math.atan2(deltaY, deltaX);
        for (Bullet bullet : bullets) {
            bullet.update(deltaT);
            if (!bullet.alive) {
                //bullets.remove(bullet);
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (bulletTimer >= bulletSpeed) {
                bulletTimer = 0;
                bullets.add(new Bullet(x, y, rotation));
                Game.shaking.addShake(new Shaking.Shake(0.2f, 5));
            }
        }
        bulletTimer += deltaT;
    }


}
