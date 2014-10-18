package com.deeep.jam.classes;

import com.deeep.jam.entities.Enemy;

import java.util.ArrayList;

/**
 * Created by Elmar on 18-10-2014.
 */
public abstract class Formations {
    protected int amount;

    public Formations(int amount, int enemyType) {
        this.amount = amount;
    }

    public abstract void addEnemies(ArrayList<Enemy> enemyList);

    public class LineFormation extends Formations {

        public LineFormation(int amount, int enemyType) {
            super(amount, enemyType);
        }

        @Override
        public void addEnemies(ArrayList<Enemy> enemyList) {
            for (int i = 0; i < amount; i++) {

            }
        }
    }
}
