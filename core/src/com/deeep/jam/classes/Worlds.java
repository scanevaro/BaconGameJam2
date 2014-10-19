package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Bullets.BigBullet;
import com.deeep.jam.entities.Bullets.SmallBullet;
import com.deeep.jam.entities.Enemy;
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
                if (contact.getFixtureA().getBody().getUserData() instanceof Enemy && contact.getFixtureB().getBody().getUserData() instanceof SmallBullet) {
                    SmallBullet bullet = (SmallBullet) contact.getFixtureB().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureA().getBody().getUserData();
                    enemy.takeDamage(1);
                    bullet.alive = false;
                }
                if (contact.getFixtureA().getBody().getUserData() instanceof SmallBullet && contact.getFixtureB().getBody().getUserData() instanceof Enemy) {
                    SmallBullet bullet = (SmallBullet) contact.getFixtureA().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureB().getBody().getUserData();
                    enemy.takeDamage(1);
                    bullet.alive = false;
                }
                if (contact.getFixtureA().getBody().getUserData() instanceof Enemy && contact.getFixtureB().getBody().getUserData() instanceof BigBullet) {
                    BigBullet bullet = (BigBullet) contact.getFixtureB().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureA().getBody().getUserData();
                    enemy.takeDamage(3);
                    bullet.alive = false;
                }
                if (contact.getFixtureA().getBody().getUserData() instanceof BigBullet && contact.getFixtureB().getBody().getUserData() instanceof Enemy) {
                    BigBullet bullet = (BigBullet) contact.getFixtureA().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureB().getBody().getUserData();
                    enemy.takeDamage(3);
                    bullet.alive = false;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }


            //( ͡° ͜ʖ ͡°) < l'elmar face
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                if (contact.getFixtureA().getBody().getUserData() instanceof EnemySmall && contact.getFixtureB().getBody().getUserData() instanceof EnemySmall) {
                    EnemySmall e1 = (EnemySmall) contact.getFixtureA().getBody().getUserData(), e2 = (EnemySmall) contact.getFixtureB().getBody().getUserData();
                    if (e1.x < e2.x) {
                        e1.x -= 0.5;
                        e2.x += 0.5;
                    } else {
                        e1.x += 0.5;
                        e2.x -= 0.5;
                    }
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        enemySpawner = new EnemySpawn();
        ship = new Ship();
        Camera.getCamera().followShip(ship);

    }

    public void draw() {
        map.render(spriteBatch);
        ship.draw(spriteBatch);
        debugRenderer.render(world, Camera.getCamera().getProjectionMatrix());
        spriteBatch.begin();
        enemySpawner.render(spriteBatch);
        spriteBatch.end();
    }

    public void update(float delta) {
        updateShip(delta);
        world.step(Gdx.graphics.getDeltaTime(), 0, 3);
        enemySpawner.update(delta);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }


}