package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.EnemySmall;
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
    private EnemySmall enemySmall;
    private ArrayList<Enemy> enemies;
    private Box2DDebugRenderer debugRenderer;
    private Map map;
    private EnemySpawn enemySpawner;

    public Worlds() {
        map = new Map();
        debugRenderer = new Box2DDebugRenderer();
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //System.out.println(contact.getFixtureA().getBody().getUserData() + " " + contact.getFixtureB().getBody().getUserData());
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        this.camera = ((Game) Gdx.app.getApplicationListener()).getCamera();
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        enemySpawner = new EnemySpawn();
        enemies = new ArrayList<Enemy>();
        ship = new Ship();
        enemies.add(new EnemySmall(240, 240, 0, 0));

    }

    public void draw() {
        map.render(spriteBatch);
        ship.draw(spriteBatch);
        debugRenderer.render(world, camera.combined);
        spriteBatch.begin();
        //for (Enemy enemy : enemies) {
        //    enemy.draw(spriteBatch);
        //}
        enemySpawner.render(spriteBatch);
        spriteBatch.end();
    }

    public void update(float delta) {
        updateShip(delta);
        enemySpawner.spawn(0);
        world.step(Gdx.graphics.getDeltaTime(), 0, 3);
        enemySpawner.update(delta);

        camera.position.set(Math.min(Math.max((int) ship.x, Game.VIRTUAL_WIDTH / 2), Map.sizeX - Game.VIRTUAL_WIDTH), Math.min(Math.max((int) ship.y, Game.VIRTUAL_HEIGHT / 2), Map.sizeY - Game.VIRTUAL_HEIGHT), 0);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }


}