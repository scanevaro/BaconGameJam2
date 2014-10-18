package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by scanevaro on 17/10/2014.
 */
public abstract class Enemy {
    public float x, y;
    public float force;
    public float rotation;
    private TextureRegion textureRegion;


    protected Enemy(float x, float y, float force, float rotation) {
        this.x = x;
        this.y = y;
        this.force = force;
        this.rotation = rotation;
    }

    public void update(float delta) {
        x += Math.cos(rotation) * delta * force;
        y += Math.sin(rotation) * delta * force;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (textureRegion != null) {
            if (!spriteBatch.isDrawing()) System.out.println("Forgot start at enemny");
            spriteBatch.draw(textureRegion, x, y, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, rotation);
        }
    }
}
