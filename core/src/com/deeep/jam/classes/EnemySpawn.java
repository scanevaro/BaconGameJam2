package com.deeep.jam.classes;

import com.deeep.jam.entities.Enemy;
import com.deeep.jam.entities.EnemySmall;
import com.deeep.jam.entities.EnemyTypes;

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

        public Wave(int level) {
            typeToAmount = new HashMap<Integer, Integer>();
            this.level = level;
            availableTypes = new ArrayList<Integer>();
        }

        public void spawnEnemies(ArrayList<Enemy> enemies) {
            Positioning positioning = new Positioning();

            for (int i = 0; i < availableTypes.size(); i++) {
                switch (availableTypes.get(i)) {
                    case EnemyTypes.ENEMY_SMALL:
                        for (int e = 0; e < typeToAmount.get(availableTypes.get(i)); e++) {
                            positioning.calculate();
                            enemies.add(new EnemySmall(positioning.x, positioning.y, positioning.force, positioning.rotation));
                            break;
                        }
                }
            }
        }



        class Positioning {
            Random random = new Random();
            boolean horizontal = random.nextBoolean();
            boolean flip = random.nextBoolean();
            public int x = 0, y = 0;
            public float rotation, force;

            public void calculate() {
                if (flip && horizontal) {
                    //downwards
                    rotation = (float) (Math.PI * 1.5f);
                    x = random.nextInt(Map.sizeX);
                } else if (horizontal) {
                    //upwards
                    rotation = (float) (Math.PI * .5f);
                    x = random.nextInt(Map.sizeX);
                } else if (flip) {
                    rotation = (float) (Math.PI * 1.0f);
                    //left to right
                    y = random.nextInt(Map.sizeY);
                } else {
                    rotation = (float) (Math.PI * 2f);
                    //right to left
                    y = random.nextInt(Map.sizeY);
                }
                force = 100;
            }
        }


    }
}
