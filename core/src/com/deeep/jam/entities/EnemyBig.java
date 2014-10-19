package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.math.PositionVector;

/**
 * Created by Andreas on 10/19/2014.
 */
public class EnemyBig extends Enemy {

    TextureRegion deadSprite;

    public EnemyBig(float x, float y, float force, float rotation) {
        super(x, y, force, rotation);
        actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_small_body"));
        setBox2DProperties(Assets.getAssets().getRegion("ship_small_body"));
        deadSprite = Assets.getAssets().getRegion("ship_small_body_destroyed");
    }

    public EnemyBig(PositionVector p, float force) {
        super(p, force);
        actuallyFuckingSetTheSprite(Assets.getAssets().getRegion("ship_small_body"));
        setBox2DProperties(Assets.getAssets().getRegion("ship_small_body"));
        deadSprite = Assets.getAssets().getRegion("ship_small_body_destroyed");
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
