package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.entities.guns.SmallCanon;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Ship {
    private TextureRegion textureRegion;
    public float x, y;
    public float rotation;
    public float force;
    public final float acceleration = 0.5f;
    public final float friction = acceleration / 2;
    public final float maxForce = 2;
    public SmallCanon[] guns = new SmallCanon[5];

    public Ship() {
        force = x = y = rotation = 0;
        textureRegion = Assets.getAssets().getRegion("ship_large_body");
        for (int i = 0; i < 5; i++) {
            guns[i] = new SmallCanon(i);
        }
    }

    public void update(float deltaT) {
        if (force > deltaT * friction) {
            force -= deltaT * friction;
        } else if (force < -deltaT * friction) {
            force += deltaT * friction;
        } else {
            force = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += deltaT * (force / maxForce);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= deltaT * (force / maxForce);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (force < maxForce) {
                force += deltaT * acceleration;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (force > maxForce / 5) {
                force -= deltaT * acceleration / 2;
            }
        }
        x += Math.cos(rotation) * force;
        y += Math.sin(rotation) * force;
        //System.out.println(this);

        for (int i = 0; i < 5; i++) {
            guns[i].update(this, deltaT);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion, x, y, textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, (float) Math.toDegrees(rotation - Math.PI / 2));
        for (int i = 0; i < 5; i++) {
            guns[i].render(spriteBatch);
        }
        spriteBatch.end();
    }

    public String toString() {
        return "rot force (x, y)" + rotation + " " + force + " (" + x + ", " + y + ")";
    }
}
