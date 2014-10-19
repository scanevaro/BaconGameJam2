package com.deeep.jam.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by Andreas on 10/19/2014.
 */
public abstract class Effect {

    public float x, y, rotation, timer, interval;

    public ArrayList<TextureRegion> sequence;
    public TextureRegion currentAnimation;

    public Effect(float x, float y, float interval, ArrayList<TextureRegion> sequence) {
        this.x = x;
        this.y = y;
        this.interval = interval;
        this.sequence = sequence;
    }

    public void render(SpriteBatch spriteBatch) {

    }

    public void update(float delta) {
        timer += delta;
        currentAnimation = sequence.get((int) (timer % interval));
        if (timer > sequence.size() * interval) timer = 0;
    }

}
