package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Ship {
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
            rotation += deltaT * 2 * (force / maxForce);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= deltaT * 2 * (force / maxForce);
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

    public String toString() {
        return "rot force (x, y)" + rotation + " " + force + " (" + x + ", " + y + ")";
    }
}

