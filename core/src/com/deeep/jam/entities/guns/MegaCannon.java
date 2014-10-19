package com.deeep.jam.entities.guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Bullets.HugeBullet;
import com.deeep.jam.entities.Gun;

import java.util.ArrayList;

/**
 * Created by Andreas on 10/18/2014.
 */
public class MegaCannon extends Gun {
    public static final int MAX_LEVEL = 1;

    public MegaCannon(int socketId) {
        super(1);
        damage = 15;
        guns = new Sprite[1][2];
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_huge"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_huge_destroyed"));
        switch (socketId) {
            case 0:
                offX = 0;
                offY = 63 / 2;
                break;
        }
        maxLevel = MAX_LEVEL;
    }

    @Override
    public void shootBullet(ArrayList<Bullet> bullets) {
        bullets.add(new HugeBullet((float) Math.toRadians(rotation), 500, x, y, damage));
    }

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
