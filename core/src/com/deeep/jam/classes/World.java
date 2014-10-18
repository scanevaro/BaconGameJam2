package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.jam.Game;
import com.deeep.jam.entities.Bullet;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.Ship;

import java.util.ArrayList;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class World {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Ship ship;

    private EnemySpawner enemySpawner;
    private ArrayList<Enemy> enemies;
    public int waterTimer = 0;

    private TextureRegion waterSprite0;
    private TextureRegion waterSprite1;

    public World(boolean debug) {
        this.camera = ((Game) Gdx.app.getApplicationListener()).getCamera();
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();
        shapeRenderer = new ShapeRenderer();
        enemies = new ArrayList<Enemy>();
        ship = new Ship();
        enemySpawner = new EnemySpawner();
        enemySpawner.addFormation(new Formations.LineFormation(enemies, 5, 1, 0));
        enemySpawner.addFormation(new Formations.LineFormation(enemies, 5, 1, 5));
        enemySpawner.addFormation(new Formations.LineFormation(enemies, 5, 1, 3));
        waterSprite0 = new TextureRegion(new Texture(Gdx.files.internal("water0.png")));
        waterSprite1 = new TextureRegion(new Texture(Gdx.files.internal("water1.png")));
    }

    public void draw() {

        spriteBatch.begin();
        for (int y = 0; y < Game.VIRTUAL_HEIGHT; y += 32) {
            for (int x = 0; x < Game.VIRTUAL_WIDTH; x += 96) {
                if (waterTimer < 10)
                    spriteBatch.draw(waterSprite0, x, y);
                else
                    spriteBatch.draw(waterSprite1, x, y);
            }
        }
        if (waterTimer > 20) waterTimer = 0;
        spriteBatch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLUE);
        // Render Ship
        for (Enemy enemy : enemies) {
            shapeRenderer.rect(enemy.x, enemy.y, 10, 10, 20, 40, 1, 1, (float) Math.toDegrees(enemy.rotation - Math.PI / 2));
        }
        shapeRenderer.rect(ship.x, ship.y, 10, 10, 20, 40, 1, 1, (float) Math.toDegrees(ship.rotation - Math.PI / 2));
        shapeRenderer.setColor(Color.RED);
        // Render Ship Gun

        shapeRenderer.rect(ship.gun.x, ship.gun.y, 5, 5, 10, 20, 1, 1, (float) Math.toDegrees(ship.gun.rotation - Math.PI / 2));
        // Render Bullets
        for (Bullet bullet : ship.gun.bullets) {
            shapeRenderer.rect(bullet.x, bullet.y, 5, 5, 2, 8, 1, 1, (float) Math.toDegrees(bullet.rotation - Math.PI / 2));
        }
        shapeRenderer.end();



        waterTimer++;
        ship.draw(spriteBatch);
    }

    public void update(float delta) {
        updateShip(delta);
        enemySpawner.update(delta);
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
        //camera.position.set(ship.x, ship.y, 0);
    }

    private void updateShip(float delta) {
        ship.update(delta);
    }


}