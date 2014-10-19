package com.deeep.jam.entities.guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Bullets.SmallBullet;
import com.deeep.jam.entities.Gun;

import java.util.ArrayList;

/**
 * Created by Andreas on 10/18/2014.
 */
public class SmallCanon extends Gun {

    public static final int MAX_LEVEL = 3;
    public SmallCanon(int socketId) {
        super(0.15f);
        damage = 1;
        guns = new Sprite[3][2];
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray_destroyed"));
        guns[1][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_green"));
        guns[1][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_green_destroyed"));
        guns[2][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_red"));
        guns[2][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_red_destroyed"));
        switch (socketId) {
            case 0:
                offX = 0/2;
                offY = 95/2;
                break;
            case 1:
                offX = -45/2;
                offY = -45/2;
                break;
            case 2:
                offX = 45/2;
                offY = -45/2;
                break;
            case 3:
                offX = -45/2;
                offY = -70/2;
                break;
            case 4:
                offX = 45/2;
                offY = -70/2;
                break;
        }
        maxLevel = MAX_LEVEL;
    }

    //Function powered by: ( ͡° ͜ʖ ͡°) < l'elmar face

    @Override
    public void shootBullet(ArrayList<Bullet> bullets) {
        bullets.add(new SmallBullet((float) Math.toRadians(rotation), 800, x, y, damage));
        //shoot sound
        if (!Game.MUTE)
            Assets.getAssets().getSmall().play();
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
