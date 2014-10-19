package com.deeep.jam.classes;

import com.deeep.jam.math.PositionVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Andreas on 10/19/2014.
 */
public class Wave {
    private HashMap<Integer, Integer> typeToAmount;
    private ArrayList<Integer> availableTypes;
    private Random random;

    private int ship1sCount, ship2sCount, ship1fCount, ship2fCount;
    private float healthMultiplier, interval, timer, shipTypeManager;
    private boolean spawning;

    public static final Wave WAVE_0 = new Wave(15, 4, 0, 0, 1, 60);


    public Wave(int s1C, int s2C, int s3C, int s4C, float healthMultiplier, float interval) {
        this.ship1sCount = s1C;
        this.ship1fCount = s2C;
        this.ship2sCount = s3C;
        this.ship2fCount = s4C;
        this.healthMultiplier = healthMultiplier;
        this.interval = interval;
        random = new Random();
    }

    public void spawn(int index) {
        spawning = true;

    }

    private PositionVector randomizePositionVector(PositionVector p) {
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
}