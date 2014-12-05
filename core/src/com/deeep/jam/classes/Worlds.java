package com.deeep.jam.classes;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Effects;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.EnemySmall;
import com.deeep.jam.entities.Ship;
import com.deeep.jam.screens.GameScreen;

import static com.deeep.jam.screens.GameScreen.showShop;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class Worlds {
    private float gamingOvertimer = 0f;
    private float degradingTimer = 0;
    private boolean flashingGameOver = false;
    private boolean beenGameOver = false;
    public static boolean day;
    public static boolean gettingDay;
    private static float dayNightStateTime;
    private static boolean outsideOfScreen = false;
    private SpriteBatch batch;
    public static Ship ship;
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    public static RayHandler rayHandler;
    private Map map;
    private EnemySpawn enemySpawner;
    private Color daylightColour;
    private ShapeRenderer shapeRenderer;
    private DeadShips deadShips;


    public Worlds() {
        deadShips = new DeadShips();
        this.batch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        shapeRenderer = new ShapeRenderer();
        daylightColour = new Color(1, 1, 1, 1);

        {//set day or night
            setDay(true);
            dayNightStateTime = 0.8f;
        }

        map = new Map();
        debugRenderer = new Box2DDebugRenderer();
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);
        RayHandler.setGammaCorrection(false);
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.5f);
        rayHandler.setBlurNum(0);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() instanceof Enemy && contact.getFixtureB().getBody().getUserData() instanceof Bullet) {
                    Bullet bullet = (Bullet) contact.getFixtureB().getBody().getUserData();
                    Enemy enemy = (Enemy) contact.getFixtureA().getBody().getUserData();
                    enemy.takeDamage(bullet.damage);
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

        enemySpawner = new EnemySpawn();
        ship = new Ship();
        Camera.getCamera().followShip(ship);
    }

    public void draw() {
        batch.setColor(1, 1, 1, 1);


        map.render(batch);
        ship.draw(batch);

//        if (Game.DEBUG)
//            debugRenderer.render(world, Camera.getCamera().getProjectionMatrix());

        batch.begin();
        enemySpawner.render(batch);
        Effects.getEffects().render(batch);
        if(beenGameOver)
            deadShips.draw(batch);
        batch.end();
        if (outsideOfScreen) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.9f, 0, 0, 0.6f);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        if (gamingOvertimer > 0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1f, 1, 1, gamingOvertimer);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        rayHandler.setCombinedMatrix(batch.getProjectionMatrix());
        rayHandler.update();
        rayHandler.render();

    }

    public void update(float delta) {
        if (Game.GAME_OVER) {
            if (!flashingGameOver) {
                gamingOvertimer = 1;
                degradingTimer += delta;
                if (degradingTimer >= 0.1f) {
                    gamingOvertimer = 0;
                    if(degradingTimer >= 0.8f){
                        gamingOvertimer = 1;
                        if(degradingTimer >= 0.9f){
                            gamingOvertimer = 0;
                            if(degradingTimer >= 1.1f){
                                gamingOvertimer = 1;
                                if(degradingTimer >= 1.2f){
                                    gamingOvertimer = 0;
                                    flashingGameOver = true;
                                }
                            }
                        }
                    }
                }
                if (flashingGameOver) {
                    gamingOvertimer = 0;
                    degradingTimer = 0;
                }
            } else {
                if (!beenGameOver)
                    gamingOvertimer += delta;
                else {
                    Camera.getCamera().followShip(null);
                    DeadShips.playerDied(ship);
                    degradingTimer += delta * 2;

                    if (gamingOvertimer <= 0) {
                        if(dayNightStateTime<1f)
                            dayNightStateTime-=delta/2;
                        daylightColour.r = dayNightStateTime;
                        daylightColour.g = dayNightStateTime;
                        daylightColour.b = dayNightStateTime;
                        System.out.println(dayNightStateTime);
                        rayHandler.setAmbientLight(daylightColour);

                        if (degradingTimer >= 4)
                            GameScreen.prepareGameOverDialog();
                    } else {
                        if (degradingTimer >= 1 + (1 - gamingOvertimer)) {
                            degradingTimer -= 1;
                            gamingOvertimer -= 0.1f;
                            if(gamingOvertimer<=0){
                                world.clearForces();
                                Worlds.setOutsideOfScreen(false);
                            }
                        }
                    }
                }
                if (gamingOvertimer >= 2) {
                    beenGameOver = true;
                    gamingOvertimer = 1;
                }
            }
        } else {
            setDayNight(delta);
            updateShip(delta);
            world.step(Gdx.graphics.getDeltaTime(), 0, 3);
            enemySpawner.update(delta);
            Effects.getEffects().update(delta);
        }
    }

    private void setDayNight(float delta) {
        if (Worlds.gettingDay) {
            if (dayNightStateTime < 1f) {
                dayNightStateTime += delta / 6;

            } else {
                dayNightStateTime = 1;
                day = true;
            }
        } else {
            if (dayNightStateTime > 0) {
                dayNightStateTime -= delta / 6;
            } else {
                if (day)
                    showShop();
                day = false;
            }
        }
        daylightColour.r = dayNightStateTime;
        daylightColour.g = dayNightStateTime;
        daylightColour.b = dayNightStateTime;
        rayHandler.setAmbientLight(daylightColour);
    }


    private void updateShip(float delta) {
        ship.update(delta);
    }

    public void resize(int width, int height) {

    }

    public static boolean isDay() {
        return day;
    }

    public static void setDay(boolean day) {
        Worlds.gettingDay = day;

        if (day) dayNightStateTime = 0;
        else dayNightStateTime = 1;
    }

    public static boolean isGettingDay() {
        return gettingDay;
    }

    public static void setOutsideOfScreen(boolean outsideOfScreen) {
        Worlds.outsideOfScreen = outsideOfScreen;
    }

    public static void gameOver() {

    }
}