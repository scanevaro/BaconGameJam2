package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.EnemyBig;
import com.deeep.jam.entities.EnemySmall;
import com.deeep.jam.math.PositionVector;
import com.deeep.jam.screens.GameScreen;

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
    private int waveNr = 0, mobsSpawned = 0;
    public boolean spawning;
    public float spawnTimer, spawnInterval;
    private Random random;

    public EnemySpawn() {
        waves = new ArrayList<Wave>();
        waves.add(Wave.WAVE_0);
        waves.add(Wave.WAVE_1);
        waves.add(Wave.WAVE_2);
        waves.add(Wave.WAVE_3);
        waves.add(Wave.WAVE_4);
        waves.add(Wave.WAVE_5);
        waves.add(Wave.WAVE_6);
        waves.add(Wave.WAVE_7);
        waves.add(Wave.WAVE_8);
        waves.add(Wave.WAVE_9);
        waves.add(Wave.WAVE_10);
        waves.add(Wave.WAVE_11);
        waves.add(Wave.WAVE_12);
        waves.add(Wave.WAVE_13);
        waves.add(Wave.WAVE_14);
        waves.add(Wave.WAVE_15);
        waves.add(Wave.WAVE_16);
        waves.add(Wave.WAVE_17);
        waves.add(Wave.WAVE_18);
        waves.add(Wave.WAVE_19);
        waves.add(Wave.WAVE_20);
        random = new Random();
    }

    public void update(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta);
            if (enemy.getHealth() <= 0) {
                if (enemy.body != null) {
                    Worlds.world.destroyBody(enemy.body);
                    enemy.body = null;
                }
                remove.add(enemy);
            }
        }
        GameScreen.wave.setText(waveNr + "");
        remove.clear();
        if (enemies.isEmpty()) {
            if (spawning) {
                Worlds.setDay(false);
            }
            spawning = false;
            System.out.println("Enemies are missing, " + waveNr);
            if (Worlds.isDay()) {
                startSpawning(waveNr);
                waveNr++;

                if (Achievements.noHits) Achievements.noHits(true);
                if (waveNr == 10) Achievements.wave10(true);
                if (Achievements.dontShoot) Achievements.dontShoot(true);
                if (Achievements.dontMove) Achievements.dontMove(true);
            }

            //iterate between day and night when wave is over
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
        if (spawnTimer > mobsSpawned * spawnInterval || spawnTimer == 0) {
            if (A_ship1sCount < ship1sCount) {
                enemies.add(new EnemySmall(randomizePositionVector(), false));
                System.out.println("Spawned a small slow enemy");
                A_ship1sCount++;
                mobsSpawned++;
            } else if (A_ship1fCount < ship1fCount) {
                enemies.add(new EnemySmall(randomizePositionVector(), true));
                System.out.println("Spawned a small fast enemy");
                A_ship1fCount++;
                mobsSpawned++;
            } else if (A_ship2sCount < ship2sCount) {
                enemies.add(new EnemyBig(randomizePositionVector(), false));
                System.out.println("Spawned a medium slow enemy");
                A_ship2sCount++;
                mobsSpawned++;
            } else if (A_ship2fCount < ship2fCount) {
                enemies.add(new EnemyBig(randomizePositionVector(), true));
                System.out.println("Spawned a medium fast enemy");
                A_ship2fCount++;
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
        if (spawning) {
            return;
        }
        if (waveId > 20) {
            spawning = true;
            int increment = waveId - 20;
            Wave wave = waves.get(20);
            this.ship1sCount = wave.ship1sCount + 5 * increment;
            this.ship1fCount = wave.ship1fCount + 5 * increment;
            this.ship2sCount = wave.ship2sCount + 5 * increment;
            this.ship2fCount = wave.ship2fCount + 5 * increment;
            this.spawnInterval = wave.interval - 0.02F * increment;
            A_ship1sCount = 0;
            A_ship1fCount = 0;
            A_ship2sCount = 0;
            A_ship2fCount = 0;
            spawnTimer = 0;
            mobsSpawned = 0;
        } else {
            spawning = true;
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
            spawnTimer = 0;
            mobsSpawned = 0;
        }

    }

    public void render(SpriteBatch spriteBatch) {
        for (Enemy enemy : enemies) {
            enemy.draw(spriteBatch);
        }
    }
}