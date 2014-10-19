package com.deeep.jam.entities;

import com.deeep.jam.classes.Assets;
import com.deeep.jam.math.PositionVector;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySmall extends Enemy {

    public EnemySmall(PositionVector p, boolean isFast) {
        super(p, 0, 10);
        if (isFast) {
            super.setForce(100F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_small_b_body"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_small_b_body"));
            deadSprite = Assets.getAssets().getRegion("ship_small_body_b_destroyed");
        } else {
            super.setForce(50F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_small_body"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_small_body"));
            deadSprite = Assets.getAssets().getRegion("ship_small_body_destroyed");
        }
    }

    protected void startDecay() {
        decaying = true;
        decayCounter = 255;
    }

    public void update(float delta) {
        super.update(delta);
    }

}
