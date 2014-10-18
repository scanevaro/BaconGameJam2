package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.classes.Assets;

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
    public Gun gun;

    public Ship() {
        force = x = y = rotation = 0;
        textureRegion = Assets.getAssets().getRegion("ship_large_body");
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
        gun.update(this, deltaT);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion, x, y, textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 0.4f, 0.4f, (float) Math.toDegrees(rotation - Math.PI / 2));
        spriteBatch.end();
        gun.render(spriteBatch);
    }

    public String toString() {
        return "rot force (x, y)" + rotation + " " + force + " (" + x + ", " + y + ")";
    }
}
