package com.deeep.jam.entities.guns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.entities.Gun;
import com.deeep.jam.entities.Ship;

/**
 * Created by Andreas on 10/18/2014.
 */
public class SmallCanon extends Gun {

    TextureRegion[][] guns = new TextureRegion[3][2];

    public SmallCanon(int socketId) {
        guns[0][0] = Assets.getAssets().getRegion("ship_gun_gray");
        guns[0][1] = Assets.getAssets().getRegion("ship_gun_gray_destroyed");
        guns[1][0] = Assets.getAssets().getRegion("ship_gun_green");
        guns[1][1] = Assets.getAssets().getRegion("ship_gun_green_destroyed");
        guns[2][0] = Assets.getAssets().getRegion("ship_gun_red");
        guns[2][1] = Assets.getAssets().getRegion("ship_gun_red_destroyed");
    }

    @Override
    public void update(Ship ship, float deltaT) {
        this.x = (float) (ship.x + Math.cos(ship.rotation) * 2);
        this.y = (float) (ship.y + Math.sin(ship.rotation) * 2);
        float deltaX = Gdx.input.getX() - x - 32;
        float deltaY = Gdx.graphics.getHeight() - Gdx.input.getY() - y - 22;
        rotation = (float) Math.atan2(deltaY, deltaX);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(guns[level][(damaged) ? 1 : 0], x, y);
        spriteBatch.end();
    }
}
