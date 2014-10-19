package com.deeep.jam.entities;

import com.deeep.jam.classes.Assets;
import com.deeep.jam.math.PositionVector;

/**
 * Created by Andreas on 10/19/2014.
 */
public class EnemyBig extends Enemy {

    public EnemyBig(PositionVector p, boolean isFast) {
        super(p, 0, 50, 20);
        this.cashMoneyBitch = 50;
        if (isFast) {
            super.setForce(160F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_medium_body_b"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_medium_body_b"));
            deadSprite = Assets.getAssets().getRegion("ship_medium_body_b_destroyed");
        } else {
            super.setForce(80F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_medium_body"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_medium_body"));
            deadSprite = Assets.getAssets().getRegion("ship_medium_body_destroyed");
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