package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.entities.Gun;

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
        gun = new Gun();
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("ship.png")));
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
        spriteBatch.draw(textureRegion, x, y - 20, 32, 32, 64, 64, 1, 1, (float) Math.toDegrees(rotation - Math.PI / 2));
        spriteBatch.end();
        gun.render(spriteBatch);
    }

    public String toString() {
        return "rot force (x, y)" + rotation + " " + force + " (" + x + ", " + y + ")";
    }
}
