package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.entities.Enemy;

import java.util.ArrayList;
import java.util.Iterator;

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
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().decayCounter <= 0)
                iterator.remove();
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

}
