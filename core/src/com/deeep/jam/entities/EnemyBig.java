package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.math.PositionVector;

/**
 * Created by Andreas on 10/19/2014.
 */
public class EnemyBig extends Enemy {

    TextureRegion deadSprite;

    public EnemyBig(PositionVector p, boolean isFast) {
        super(p, 0);
        if (isFast) {
            super.setForce(5F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_medium_body_b"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_medium_body_b"));
            deadSprite = Assets.getAssets().getRegion("ship_medium_body_b_destroyed");
        } else {
            super.setForce(2.5F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_medium_body"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_medium_body"));
            deadSprite = Assets.getAssets().getRegion("ship_medium_body_destroyed");
        }
    }

    public void die() {
        if (sinking) return;
        sinking = true;
        actuallyFuckingSetTheSprite(deadSprite);
        finalRotation = (float) Math.toDegrees(rotation - Math.PI / 2);
        startDecay();
    }

    protected void startDecay() {
        decaying = true;
        decayCounter = 255;
    }

    public void update(float delta) {
        super.update(delta);
    }
}