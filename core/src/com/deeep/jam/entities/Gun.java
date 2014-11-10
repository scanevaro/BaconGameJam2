package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Shaking;
import com.deeep.jam.classes.Worlds;
import com.deeep.jam.entities.Bullets.Bullet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Elmar on 18-10-2014.
 */
public abstract class Gun {
    protected Sprite[][] guns;
    protected int maxLevel = 1;
    protected int level = 0;
    protected boolean damaged = false;
    public float rotation;
    public float x, y, offX, offY;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public ArrayList<Bullet> removal = new ArrayList<Bullet>();
    private ArrayList<Vector2> vector2s;
    public float bulletTimer = 0;
    public float fireRate = 0.5f;
    public float defaultFireRate = 0.5f;
    public boolean locked = false;
    private Random random = new Random();
    public float damage;

    public Gun(float fireRate) {
        this.defaultFireRate = fireRate;
        this.fireRate = fireRate;
    }


    public void update(Ship ship, float deltaT) {
        update(deltaT);
        this.x = (float) ((float) (ship.x) + offX * Math.cos(ship.rotation - Math.PI / 2) - offY * Math.sin(ship.rotation - Math.PI / 2));
        this.y = (float) ((float) (ship.y) + offX * Math.sin(ship.rotation - Math.PI / 2) + offY * Math.cos(ship.rotation - Math.PI / 2));
        bulletTimer += deltaT;
        if (Game.android == false) {
            float deltaX = (Camera.getCamera().getTouchX()) - (x - Camera.getCamera().getOrthographicCamera().position.x + Game.VIRTUAL_WIDTH / 2);
            float deltaY = (Gdx.graphics.getHeight()) - Camera.getCamera().getTouchY() - (y - Camera.getCamera().getOrthographicCamera().position.y + Game.VIRTUAL_HEIGHT / 2);
            if (!locked) {
                rotation = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
            } else {
                rotation = (float) Math.toDegrees(ship.rotation);
            }
            if (Gdx.input.isTouched()) {
                fire();
            }
        }
        for (Bullet bullet : bullets) {
            bullet.update(deltaT);
            if (bullet.isDead()) {
                removal.add(bullet);
                Worlds.world.destroyBody(bullet.body);
            }
        }
        for (Bullet bullet : removal) {
            bullets.remove(bullet);
        }
        removal.clear();
    }

    public void setRotation(int degrees) {
        rotation = degrees;
    }

    public void fire() {
        if (bulletTimer >= fireRate) {
            Camera.getCamera().getShaking().addShake(new Shaking.Shake(fireRate, fireRate));
            bulletTimer = random.nextFloat() * fireRate / 10;
            shootBullet(bullets);
        }
    }

    public void levelUp() {
        if (level < maxLevel - 1) {
            level++;
            fireRate = defaultFireRate - ((level / (1 + maxLevel)) * defaultFireRate);
            damage = damage + damage * 1.5f;
        }
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public abstract void shootBullet(ArrayList<Bullet> bullets);

    public abstract void update(float deltaT);

    public abstract void render(SpriteBatch spriteBatch);

    public TextureRegion getTexture(int level) {
        if (level > maxLevel) {
            level = maxLevel;
        }
        return guns[level][0];
    }

    public int getLevel() {
        return level;
    }
}