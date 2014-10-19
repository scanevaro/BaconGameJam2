package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Effects;
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

    public Worlds() {
        map = new Map();
        debugRenderer = new Box2DDebugRenderer();
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() instanceof Enemy && contact.getFixtureB().getBody().getUserData() instanceof Bullet) {
                    Bullet bullet = (Bullet) contact.getFixtureB().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureA().getBody().getUserData();
                    enemy.takeDamage(1);
                    bullet.hitEffect();
                    bullet.alive = false;
                } else if (contact.getFixtureA().getBody().getUserData() instanceof Bullet && contact.getFixtureB().getBody().getUserData() instanceof Enemy) {
                    Bullet bullet = (Bullet) contact.getFixtureA().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureB().getBody().getUserData();
                    bullet.hitEffect();
                    enemy.takeDamage(bullet.damage);
                    bullet.alive = false;
                } else if (contact.getFixtureA().getBody().getUserData() instanceof Ship && contact.getFixtureB().getBody().getUserData() instanceof Enemy) {
                    Enemy enemy = (Enemy) contact.getFixtureB().getBody().getUserData();
                    Ship ship = (Ship) contact.getFixtureA().getBody().getUserData();
                    ship.takeDamage(enemy.damageCausal);
                    enemy.takeDamage(100000000);
                    Effects.getEffects().addEffect(new Effects.Effect(9, enemy.x, enemy.y));
                    Effects.getEffects().addEffect(new Effects.Effect(3, enemy.x + 10, enemy.y));
                    Effects.getEffects().addEffect(new Effects.Effect(8, enemy.x + 10, enemy.y + 10));
                    Effects.getEffects().addEffect(new Effects.Effect(10, enemy.x, enemy.y + 10));
                } else if (contact.getFixtureA().getBody().getUserData() instanceof Enemy && contact.getFixtureB().getBody().getUserData() instanceof Ship) {
                    Enemy enemy = (Enemy) contact.getFixtureA().getBody().getUserData();
                    Ship ship = (Ship) contact.getFixtureB().getBody().getUserData();
                    ship.takeDamage(enemy.damageCausal);
                    enemy.takeDamage(100000000);
                    Effects.getEffects().addEffect(new Effects.Effect(9, enemy.x, enemy.y));
                    Effects.getEffects().addEffect(new Effects.Effect(3, enemy.x + 10, enemy.y));
                    Effects.getEffects().addEffect(new Effects.Effect(8, enemy.x + 10, enemy.y + 10));
                    Effects.getEffects().addEffect(new Effects.Effect(10, enemy.x, enemy.y + 10));
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
        //debugRenderer.render(world, Camera.getCamera().getProjectionMatrix());
        spriteBatch.begin();
        enemySpawner.render(spriteBatch);
        Effects.getEffects().render(spriteBatch);
        spriteBatch.end();
    }

    public void update(float delta) {
        updateShip(delta);
        world.step(Gdx.graphics.getDeltaTime(), 0, 3);
        enemySpawner.update(delta);
        Effects.getEffects().update(delta);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }


}