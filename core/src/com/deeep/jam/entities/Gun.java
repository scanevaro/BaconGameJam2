package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Shaking;

import java.util.ArrayList;

/**
 * Created by Elmar on 18-10-2014.
 */
public abstract class Gun {

    public float rotation;
    public float x, y, offX, offY;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public float bulletTimer = 0;
    public final float bulletSpeed = 0.5f;
    protected TextureRegion textureRegion;

    public Gun() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("gun.png")));
    }

    public void update(Ship ship, float deltaT) {
        this.x = (float) (ship.x + Math.cos(ship.rotation) * 2);
        this.y = (float) (ship.y + Math.sin(ship.rotation) * 2);
        float deltaX = Gdx.input.getX() - x - 32;
        float deltaY = Gdx.graphics.getHeight() - Gdx.input.getY() - y - 22;
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
                bullets.add(new Bullet(x + 32, y + 22, rotation));
                Game.shaking.addShake(new Shaking.Shake(0.2f,5));
            }
        }
        bulletTimer += deltaT;
    }

    public void render(SpriteBatch spriteBatch) {
        float theta = (float) Math.toRadians(rotation - Math.PI / 2);
        int compensationConstant = (int) (Math.PI * theta);
        spriteBatch.begin();
        spriteBatch.draw(textureRegion, x + 30, y + 12 - compensationConstant, 3, 0, 6, 14, 1, 1, (float) Math.toDegrees(rotation - Math.PI / 2));
        spriteBatch.end();
    }

}