package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.EnemySmall;
import com.deeep.jam.math.PositionVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySpawn {
    private ArrayList<Wave> waves;
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public ArrayList<Enemy> remove = new ArrayList<Enemy>();
    private int waveNo = 0;

    public EnemySpawn() {
        waves = new ArrayList<Wave>();
        waves.add(new Wave(1));
    }

    public void update(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta);
            if (enemy.getHealth() <= 0) {
                remove.add(enemy);
            }
        }
        remove.clear();
        if (enemies.isEmpty()) {
            spawn(waveNo);
            waveNo++;
        }
    }

    public void spawn(int waveId) {
        if (waveId >= waves.size()) {
            System.out.println("Already spawning next?");
            return;
        }
        waves.get(waveId).spawnEnemies(enemies);
    }

    public void render(SpriteBatch spriteBatch) {
        for (Enemy enemy : enemies) {
            enemy.draw(spriteBatch);
        }
    }

    public static class Wave {
        private HashMap<Integer, Integer> typeToAmount;
        private ArrayList<Integer> availableTypes;
        private int level;
        private int enemyCap = 10;
        private Random random;


        public Wave(int level) {
            typeToAmount = new HashMap<Integer, Integer>();
            this.level = level;
            availableTypes = new ArrayList<Integer>();
            random = new Random();
        }

        public void spawnEnemies(ArrayList<Enemy> enemies) {
            while (enemies.size() < enemyCap) {
                PositionVector p = randomizePositionVector(new PositionVector());
                enemies.add(new EnemySmall(p, 50));
            }
        }

        private PositionVector randomizePositionVector(PositionVector p) {
            double i = Math.random();
            if (i > 0.5) {
                //stuck on walls
                if (random.nextInt(2) == 0)
                    p.x = 0;
                else
                    p.x = Map.sizeX + 20;
                p.y = random.nextInt((int) Math.floor(Map.sizeY));
            } else {
                //stuck on floor/ceiling
                if (random.nextInt(2) == 0)
                    p.y = 0;
                else
                    p.y = Map.sizeY + 20;
                p.x = random.nextInt((int) Math.floor(Map.sizeX));
            }
            return p;
        }
    }
}
