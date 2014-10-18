package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.Ship;

import java.util.ArrayList;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class Worlds {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Ship ship;
    public static World world;

    private ArrayList<Enemy> enemies;
    private Box2DDebugRenderer debugRenderer;

    public Worlds() {
        debugRenderer = new Box2DDebugRenderer();
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);
        this.camera = ((Game) Gdx.app.getApplicationListener()).getCamera();
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        enemies = new ArrayList<Enemy>();
        ship = new Ship();

    }

    public void draw() {
        ship.draw(spriteBatch);
        debugRenderer.render(world, camera.combined);
    }

    public void update(float delta) {
        updateShip(delta);
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
        world.step(Gdx.graphics.getDeltaTime(), 0, 3);

        //camera.position.set(ship.x, ship.y, 0);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }


}