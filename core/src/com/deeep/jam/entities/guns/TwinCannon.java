package com.deeep.jam.entities.guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Gun;

import java.util.ArrayList;

/**
 * Created by Elmar on 19-10-2014.
 */
public class TwinCannon extends Gun {
    Sprite[][] guns = new Sprite[2][2];
    public TwinCannon(int socketId) {
        super(0.4f);
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_dual_gray"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_dual_gray_destroyed"));
        guns[1][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_dual_green"));
        guns[1][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_dual_green_destroyed"));
        switch (socketId) {
            case 0:
                offX = 0;
                offY = 32;
                break;
        }
    }

    @Override
    public void shootBullet(ArrayList<Bullet> bullets) {

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
