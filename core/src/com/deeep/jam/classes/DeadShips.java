package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.Ship;

/**
 * Created by E on 12/4/2014.
 */
public class DeadShips {
    private static Sprite playerSprite;
    public DeadShips() {
        playerSprite = new Sprite(Assets.getAssets().getRegion("ship_large_body_destroyed"));
    }

    public void draw(SpriteBatch spriteBatch) {
        playerSprite.draw(spriteBatch);
    }

    public void update(float deltaT) {

    }

    public static void killShip(Enemy enemy){

    }
    public static void playerDied(Ship ship){
        playerSprite.setRotation((float) Math.toDegrees(ship.rotation-Math.PI / 2));
        playerSprite.setPosition(Map.sizeX/2-playerSprite.getWidth()/2,Map.sizeY/2-playerSprite.getHeight()/2);
    }
}
