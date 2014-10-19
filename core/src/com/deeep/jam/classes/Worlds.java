package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.entities.EnemySmall;
import com.deeep.jam.entities.Ship;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class Worlds {
    private SpriteBatch spriteBatch;
    public static Ship ship;
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    private Map map;
    private EnemySpawn enemySpawner;
    private EnemySmall enemySmall;

    public Worlds() {
        map = new Map();
        debugRenderer = new Box2DDebugRenderer();
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                System.out.println(contact.getFixtureA().getBody().getUserData() + " " + contact.getFixtureB().getBody().getUserData());

                //Collision 2 small enemies
                if (contact.getFixtureA().getBody().getUserData() instanceof EnemySmall && contact.getFixtureB().getBody().getUserData() instanceof EnemySmall) {
                    EnemySmall e1 = (EnemySmall) contact.getFixtureA().getBody().getUserData(), e2 = (EnemySmall) contact.getFixtureB().getBody().getUserData();
                    if (e1.x < e2.x) {
                        e1.beginCollision(-1F, 0F);
                        e2.beginCollision(1F, 0F);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() instanceof EnemySmall && contact.getFixtureB().getBody().getUserData() instanceof EnemySmall) {
                    EnemySmall e1 = (EnemySmall) contact.getFixtureA().getBody().getUserData(), e2 = (EnemySmall) contact.getFixtureB().getBody().getUserData();
                    e1.endCollision();
                    e2.endCollision();
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        enemySpawner = new EnemySpawn();
        ship = new Ship();
        Camera.getCamera().followShip(ship);
        enemySmall = new EnemySmall((Map.sizeX / 2) - 50, (Map.sizeY / 2) - 50, 50, 0);

    }

    public void draw() {
        map.render(spriteBatch);
        ship.draw(spriteBatch);
        debugRenderer.render(world, Camera.getCamera().getProjectionMatrix());
        spriteBatch.begin();
        enemySpawner.render(spriteBatch);
        enemySmall.draw(spriteBatch);
        spriteBatch.end();
    }

    public void update(float delta) {
        updateShip(delta);
        world.step(Gdx.graphics.getDeltaTime(), 0, 3);
        enemySpawner.update(delta);
        enemySmall.update(delta);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }


}