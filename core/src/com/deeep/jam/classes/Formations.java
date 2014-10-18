package com.deeep.jam.classes;

import com.deeep.jam.Game;
import com.deeep.jam.entities.BasicEnemy;
import com.deeep.jam.entities.Enemy;

import java.util.ArrayList;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Formations {
    protected ArrayList<Enemy> enemies;
    protected int amount;
    protected int enemyType;
    private float delay;
    private float timer;
    protected boolean spawned = false;

    public Formations(ArrayList<Enemy> enemies, int amount, int enemyType, float delay) {
        this.amount = amount;
        this.enemyType = enemyType;
        this.enemies = enemies;
        this.delay = delay;
    }

    public void update(float deltaT) {
        timer += deltaT;
        if (timer >= delay) {
            if (!spawned) {
                addEnemies();
            }
        }
    }

    public boolean isDone() {
        return spawned;
    }

    public void addEnemies() {
        spawned = true;
    }

    public static class VFormation extends Formations {

        public VFormation(ArrayList<Enemy> enemies, int amount, int enemyType, float delay) {
            super(enemies, amount, enemyType, delay);
        }

        @Override
        public void addEnemies() {
            switch (enemyType){
                default:
                    for (int i = 0; i < amount; i++) {
                        enemies.add(new BasicEnemy((((float) Game.VIRTUAL_WIDTH / (float) amount)) * i + (Game.VIRTUAL_WIDTH / (float) amount / 2f), Game.VIRTUAL_HEIGHT, 120, (float) (1.5f * Math.PI)));
                    }
            }
        }
    }

    public static class LineFormation extends Formations {

        public LineFormation(ArrayList<Enemy> enemies, int amount, int enemyType, float delay) {
            super(enemies, amount, enemyType, delay);
        }

        @Override
        public void addEnemies() {
            switch (enemyType) {
                default:
                    for (int i = 0; i < amount; i++) {
                        enemies.add(new BasicEnemy((((float) Game.VIRTUAL_WIDTH / (float) amount)) * i + (Game.VIRTUAL_WIDTH / (float) amount / 2f), Game.VIRTUAL_HEIGHT, 120, (float) (1.5f * Math.PI)));
                    }
            }
            spawned = true;
        }
    }

    public static class DelayFormation extends Formations {
        public DelayFormation(ArrayList<Enemy> enemies, int amount, int enemyType, float delay) {
            super(enemies, amount, enemyType, delay);
        }
    }
}
