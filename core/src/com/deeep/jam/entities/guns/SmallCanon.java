package com.deeep.jam.entities.guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.Camera;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Shaking;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Bullets.SmallBullet;
import com.deeep.jam.entities.Gun;

import java.util.ArrayList;

/**
 * Created by Andreas on 10/18/2014.
 */
public class SmallCanon extends Gun {

    Sprite[][] guns = new Sprite[3][2];

    public SmallCanon(int socketId) {
        super(0.1f);
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray_destroyed"));
        guns[1][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_green"));
        guns[1][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_green_destroyed"));
        guns[2][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_red"));
        guns[2][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_red_destroyed"));
        switch (socketId) {
            case 0:
                offX = 0;
                offY = 95;
                break;
            case 1:
                offX = -45;
                offY = -45;
                break;
            case 2:
                offX = 45;
                offY = -45;
                break;
            case 3:
                offX = -45;
                offY = -70;
                break;
            case 4:
                offX = 45;
                offY = -70;
                break;
        }
    }

    //Function powered by: ( ͡° ͜ʖ ͡°) < l'elmar face

    @Override
    public void shootBullet(ArrayList<Bullet> bullets) {
        Camera.getCamera().getShaking().addShake(new Shaking.Shake(0.1f, 1f));
        bullets.add(new SmallBullet((float) Math.toRadians(rotation), 1500, x, y));
        Assets.getAssets().getShootSound().play();
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
