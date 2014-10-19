package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.math.PositionVector;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySmall extends Enemy {

    TextureRegion deadSprite;

    public EnemySmall(PositionVector p, boolean isFast) {
        super(p, 0);
        if (isFast) {
            super.setForce(10F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_small_b_body"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_small_b_body"));
            deadSprite = Assets.getAssets().getRegion("ship_small_body_b_destroyed");
        } else {
            super.setForce(5F);
            actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_small_body"));
            setBox2DProperties(Assets.getAssets().getRegion("ship_small_body"));
            deadSprite = Assets.getAssets().getRegion("ship_small_body_destroyed");
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
