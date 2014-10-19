package com.deeep.jam.entities;

import com.deeep.jam.classes.Assets;
import com.deeep.jam.math.PositionVector;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySmall extends Enemy {

    public EnemySmall(float x, float y, float force, float rotation) {
        super(x, y, force, rotation);
        setSprite(Assets.getAssets().getRegion("ship_small_body"));
        System.out.println("spawned enemy small");
    }

    public EnemySmall(PositionVector p, float force) {
        super(p, force);
        setSprite(Assets.getAssets().getRegion("ship_small_body"));
        System.out.println("spawned enemy small");
    }

    public void die() {
        setSprite(Assets.getAssets().getRegion("ship_small_body_destroyed"));
    }

}
