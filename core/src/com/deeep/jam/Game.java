package com.deeep.jam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    Ship ship;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        ship = new Ship();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
        ship.update(Gdx.graphics.getDeltaTime());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(ship.x, ship.y, 10, 10, 20, 40, 1, 1, (float) Math.toDegrees(ship.rotation - Math.PI / 2));
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(ship.gun.x, ship.gun.y, 5, 5, 10, 20, 1, 1, (float) Math.toDegrees(ship.gun.rotation - Math.PI / 2));
        for (Bullet bullet : ship.gun.bullets) {
            shapeRenderer.rect(bullet.x, bullet.y, 5, 5, 2, 8, 1, 1, (float) Math.toDegrees(bullet.rotation - Math.PI / 2));
        }
        shapeRenderer.end();
    }
}
