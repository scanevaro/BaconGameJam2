package com.deeep.jam.classes;

import com.deeep.jam.Game;
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

    public EnemySpawn() {
        waves = new ArrayList<Wave>();
    }

    public static class Wave {
        private HashMap<Integer, Integer> typeToAmount;
        private ArrayList<Integer> availableTypes;
        private int level;
        private int enemyCap = 255;
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
                enemies.add(new EnemySmall(p, 5));
            }
        }

        private PositionVector randomizePositionVector(PositionVector p) {
            double i = Math.random();
            if (i > 0.5) {
                //stuck on walls
                if (random.nextInt(2) == 0)
                    p.x = -20;
                else
                    p.x = Game.VIRTUAL_WIDTH + 20;
                p.y = random.nextInt((int) Math.floor(Game.VIRTUAL_HEIGHT));
            } else {
                //stuck on floor/ceiling
                if (random.nextInt(2) == 0)
                    p.y = -20;
                else
                    p.y = Game.VIRTUAL_HEIGHT + 20;
                p.x = random.nextInt((int) Math.floor(Game.VIRTUAL_WIDTH));
            }
            return p;
        }

    }
}
