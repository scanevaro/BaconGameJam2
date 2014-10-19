package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Bullets.Bullet;

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
    public ArrayList<Bullet> removal = new ArrayList<Bullet>();
    public float bulletTimer = 0;
    public float fireRate = 0.5f;

    public Gun(float fireRate) {
        this.fireRate = fireRate;
    }


    public void update(Ship ship, float deltaT) {
        update(deltaT);
        this.x = (float) ((float) (ship.x) + offX * Math.cos(ship.rotation - Math.PI / 2) - offY * Math.sin(ship.rotation - Math.PI / 2));
        this.y = (float) ((float) (ship.y) + offX * Math.sin(ship.rotation - Math.PI / 2) + offY * Math.cos(ship.rotation - Math.PI / 2));
        float deltaX = (Gdx.input.getX()) - (x - Camera.getCamera().getOrthographicCamera().position.x + Game.VIRTUAL_WIDTH / 2);
        float deltaY = (Gdx.graphics.getHeight()) - Gdx.input.getY() - (y - Camera.getCamera().getOrthographicCamera().position.y + Game.VIRTUAL_HEIGHT / 2);
        rotation = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
        bulletTimer += deltaT;
        if (Gdx.input.isTouched()) {
            if (bulletTimer >= fireRate) {
                bulletTimer = 0;
                shootBullet(bullets);
            }
        }
        for (Bullet bullet : bullets) {
            bullet.update(deltaT);
            if (bullet.isDead()) {
                removal.add(bullet);
            }
        }
        for (Bullet bullet : removal) {
            bullets.remove(bullet);
        }
        removal.clear();
    }

    public abstract void shootBullet(ArrayList<Bullet> bullets);

    public abstract void update(float deltaT);

    public abstract void render(SpriteBatch spriteBatch);

}