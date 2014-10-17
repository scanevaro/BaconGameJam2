package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deeep.jam.Game;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class World {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Box2DDebugRenderer debugRenderer;

    public World(boolean debug) {
        this.camera = ((Game) Gdx.app.getApplicationListener()).getCamera();
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
    }

    public void draw() {
    }

    public void update(float delta) {
    }

    public void touched(float force) {
    }
}