package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Bullet;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.Ship;

import java.util.Random;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class World {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Ship ship;
    private Enemy[] enemy;
    private int formation;

    private Random random;

    public World(boolean debug) {
        this.camera = ((Game) Gdx.app.getApplicationListener()).getCamera();
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        shapeRenderer = new ShapeRenderer();

        ship = new Ship();

        spawnEnemys();
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        // Render Ship
        shapeRenderer.rect(ship.x, ship.y, 10, 10, 20, 40, 1, 1, (float) Math.toDegrees(ship.rotation - Math.PI / 2));
        shapeRenderer.setColor(Color.RED);
        // Render Ship Gun
        shapeRenderer.rect(ship.gun.x, ship.gun.y, 5, 5, 10, 20, 1, 1, (float) Math.toDegrees(ship.gun.rotation - Math.PI / 2));
        // Render Bullets
        for (Bullet bullet : ship.gun.bullets) {
            shapeRenderer.rect(bullet.x, bullet.y, 5, 5, 2, 8, 1, 1, (float) Math.toDegrees(bullet.rotation - Math.PI / 2));
        }
        shapeRenderer.end();

        spriteBatch.begin();
        //Render Enemys
        for (int i = 0; i < enemy.length; i++)
            enemy[i].draw(spriteBatch);

        spriteBatch.end();
    }

    public void update(float delta) {
        updateShip(delta);

        updateEnemys(delta);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }

    private void updateEnemys(float delta) {
        for (int i = 0; i < enemy.length; i++)
            enemy[i].update(delta);

        if (enemy[0].getY() < 0)
            spawnEnemys();
    }


    private void spawnEnemys() {
        if (random == null) random = new Random();
        formation = random.nextInt(3);

        enemy = new Enemy[7];
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Enemy(Assets.getAssets().getEnemy1(), formation);
            enemy[i].setPosition(0, Game.VIRTUAL_HEIGHT);
        }
    }
}