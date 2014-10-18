package com.deeep.jam.entities.guns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.entities.Gun;
import com.deeep.jam.entities.Ship;

/**
 * Created by Andreas on 10/18/2014.
 */
public class SmallCanon extends Gun {

    Sprite[][] guns = new Sprite[3][2];

    public SmallCanon(int socketId) {
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray_destroyed"));
        guns[1][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_green"));
        guns[1][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_green_destroyed"));
        guns[2][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_red"));
        guns[2][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_red_destroyed"));
        switch (socketId) {
            case 0:
                offX = 50;
                offY = 0;
        }
    }

    @Override
    public void update(Ship ship, float deltaT) {
        this.x = (float) (ship.x);
        this.y = (float) (ship.y);
        float deltaX = Gdx.input.getX() - x - 32;
        float deltaY = Gdx.graphics.getHeight() - Gdx.input.getY() - y - 22;
        this.originX = ship.x;
        this.originY = ship.y;
        //guns[level][(damaged) ? 1 : 0].setOrigin(guns[level][(damaged) ? 1 : 0].getWidth() / 2, guns[level][(damaged) ? 1 : 0].getHeight() / 2);
        guns[level][(damaged) ? 1 : 0].setPosition(originX, originY);

        theta = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));

        guns[level][(damaged) ? 1 : 0].setRotation(theta + 90);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        guns[level][(damaged) ? 1 : 0].draw(spriteBatch);
    }
}
