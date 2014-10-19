package com.deeep.jam.entities.guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.Camera;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Shaking;
import com.deeep.jam.entities.Bullets.BigBullet;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Bullets.SmallBullet;
import com.deeep.jam.entities.Gun;

import java.util.ArrayList;

/**
 * Created by Andreas on 10/18/2014.
 */
public class BigCannon extends Gun {
    Sprite[][] guns = new Sprite[2][2];

    public BigCannon(int socketId) {
        super(0.5f);
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_big_gun"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_big_gun_destroyed"));
        guns[1][0] = new Sprite(Assets.getAssets().getRegion("ship_big_gun_dual"));
        guns[1][1] = new Sprite(Assets.getAssets().getRegion("ship_big_gun_dual_destroyed"));
        switch (socketId) {
            case 0:
                offX = -32;
                offY = 26;
                break;
            case 1:
                offX = 32;
                offY = 26;
                break;
            case 2:
                offX = 0;
                offY = -43;
                break;
            case 3:
                offX = 0;
                offY = -72;
                break;
        }
    }

    @Override
    public void shootBullet(ArrayList<Bullet> bullets) {
        Camera.getCamera().getShaking().addShake(new Shaking.Shake(0.5f, 2f));
        bullets.add(new BigBullet((float) Math.toRadians(rotation), 1200, x, y));
    }

    @Override
    public void update(float deltaT) {
        guns[level][(damaged) ? 1 : 0].setPosition((x) - guns[level][(damaged) ? 1 : 0].getWidth() / 2, (y) - guns[level][(damaged) ? 1 : 0].getHeight() / 2);
        guns[level][(damaged) ? 1 : 0].setRotation(rotation + 90);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (Bullet bullet : bullets) {
            bullet.render(spriteBatch);
        }
        guns[level][(damaged) ? 1 : 0].draw(spriteBatch);
    }
}
