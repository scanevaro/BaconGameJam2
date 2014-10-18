package com.deeep.jam.entities;

import com.deeep.jam.classes.Assets;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySmall extends Enemy {
    public EnemySmall(float x, float y, float force, float rotation) {
        super(x, y, force, rotation);
        setSprite(Assets.getAssets().getRegion("ship_small_body"));
    }


}
