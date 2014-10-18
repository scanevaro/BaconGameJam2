package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.Game;

import java.util.Random;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class World {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private Sprite[] enemy;
    private int formation;

    private Random random;

    public World(boolean debug) {
        this.camera = ((Game) Gdx.app.getApplicationListener()).getCamera();
        this.spriteBatch = ((Game) Gdx.app.getApplicationListener()).getSpriteBatch();

        enemy = new Sprite[7];
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Sprite(Assets.getAssets().getEnemy1());
            enemy[i].setPosition(0, Game.VIRTUAL_HEIGHT);
        }

        random = new Random();
        formation = random.nextInt(3);
    }

    public void draw() {
        int x;
        switch (formation) {
            case 0:
                x = 100;
                for (int i = 0; i < enemy.length; i++) {
                    enemy[i].setPosition(x, enemy[i].getY());
                    enemy[i].draw(spriteBatch);

                    x += 100;
                }
                break;
            case 1:
                x = random.nextInt(150);
                for (int i = 0; i < enemy.length; i++) {
                    enemy[i].setPosition(x, enemy[i].getY());
                    enemy[i].draw(spriteBatch);

                    x += random.nextInt(50);
                }
                break;
            case 2:
                x = 100;
                int y = 20;
                for (int i = 0; i < enemy.length; i++) {
                    enemy[i].setPosition(x, enemy[i].getY() + y);
                    enemy[i].draw(spriteBatch);

                    y += 15;
                    x += 100;
                }
                break;
        }

    }

    public void update(float delta) {
        for (int i = 0; i < enemy.length; i++)
            if (enemy[i].getX() <= Game.VIRTUAL_HEIGHT)
                enemy[i].setPosition(enemy[i].getX(), enemy[i].getY() - delta * 30 /*TODO enemySpeed*/);

        if (enemy[0].getX() > Game.VIRTUAL_HEIGHT + enemy[0].getHeight())
            spawn();
    }

    private void spawn() {
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Sprite(Assets.getAssets().getEnemy1());
            enemy[i].setPosition(0, Game.VIRTUAL_HEIGHT);
        }

        formation = random.nextInt(3);
    }

    public void touched(float force) {
    }
}