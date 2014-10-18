package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.entities.Ship;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Player {
    private static Player player;

    public static Player getPlayer() {
        if (player == null)
            player = new Player();
        return player;
    }

    private String name;
    private Ship ship;

    private int cashMoney = 0;

    private Player() {

    }

    public void update(float deltaT) {

    }

    public void draw(SpriteBatch spriteBatch) {

    }

}
