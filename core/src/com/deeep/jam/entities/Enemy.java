package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by scanevaro on 17/10/2014.
 */
public class Enemy extends Sprite {
    private int formation;

    public Enemy(Texture texture, int formation) {
        super(texture);

        this.formation = formation;
    }

    public void update(float delta) {
        switch (formation) {
            case 0:
                setPosition(getX() + delta * 20, getY());
                break;
            case 1:
                setPosition(getX(), getY() + delta * 30);
                break;
            case 2:
                setPosition(getX() + delta * 20, getY() - delta * 20);
                break;
        }
    }
}
