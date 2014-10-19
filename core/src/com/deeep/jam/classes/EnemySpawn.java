package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.EnemySmall;
import com.deeep.jam.math.PositionVector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySpawn {
    private ArrayList<Wave> waves;
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public ArrayList<Enemy> remove = new ArrayList<Enemy>();
    public int ship1sCount, ship2sCount, ship1fCount, ship2fCount;
    public int A_ship1sCount, A_ship2sCount, A_ship1fCount, A_ship2fCount;
    private int waveNo = 0, mobsSpawned = 0;
    public boolean spawning;
    public float spawnTimer, spawnInterval;
    private Random random;

    public EnemySpawn() {
        waves = new ArrayList<Wave>();
        waves.add(Wave.WAVE_0);
        random = new Random();
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
            startSpawning(waveNo);
            waveNo++;
        }

        if (spawning) {
            updateSpawner(delta);
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().decayCounter <= 0)
                iterator.remove();
        }
    }

    private void updateSpawner(float delta) {

        if (spawnTimer > mobsSpawned * spawnInterval) {
            if (A_ship1sCount < ship1sCount) {

                mobsSpawned++;
            } else if (A_ship1sCount < ship1fCount) {
                enemies.add(new EnemySmall(randomizePositionVector(), false));
                mobsSpawned++;
            } else if (A_ship1sCount < ship2sCount) {
                enemies.add(new EnemySmall(randomizePositionVector(), false));
                mobsSpawned++;
            } else if (A_ship1sCount < ship2fCount) {

                mobsSpawned++;
            }
        }


        spawnTimer += delta;
    }

    private PositionVector randomizePositionVector() {
        PositionVector p = new PositionVector();
        double i = Math.random();
        if (i > 0.5) {
            //stuck on walls
            if (random.nextInt(2) == 0)
                p.x = random.nextInt(500) - 250;
            else
                p.x = Map.sizeX + random.nextInt(800) - 400;
            p.y = random.nextInt((int) Math.floor(Map.sizeY));
        } else {
            //stuck on floor/ceiling
            if (random.nextInt(2) == 0)
                p.y = random.nextInt(500) - 250;
            else
                p.y = Map.sizeY + random.nextInt(800) - 400;
            p.x = random.nextInt((int) Math.floor(Map.sizeX));
        }
        return p;
    }

    public void startSpawning(int waveId) {
        if (waveId >= waves.size() || spawning) {
            System.out.println("Already spawning next?");
            return;
        }
        spawning = false;
        Wave wave = waves.get(waveId);
        this.ship1sCount = wave.ship1sCount;
        this.ship1fCount = wave.ship1fCount;
        this.ship2sCount = wave.ship2sCount;
        this.ship2fCount = wave.ship2fCount;
        this.spawnInterval = wave.interval;
        A_ship1sCount = 0;
        A_ship1fCount = 0;
        A_ship2sCount = 0;
        A_ship2fCount = 0;
        spawnTimer = mobsSpawned = 0;
    }

    public void render(SpriteBatch spriteBatch) {
        for (Enemy enemy : enemies) {
            enemy.draw(spriteBatch);
        }
    }

}
