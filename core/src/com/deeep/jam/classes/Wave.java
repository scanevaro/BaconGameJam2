package com.deeep.jam.classes;

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

    public int ship1sCount, ship2sCount, ship1fCount, ship2fCount;
    public float healthMultiplier, interval, timer, shipTypeManager;
    public static final Wave WAVE_0 = new Wave(15, 4, 0, 0, 1, 0.1F);
    public static final Wave WAVE_1 = new Wave(20, 8, 2, 0, 1, 0.1F);


    public Wave(int s1C, int s2C, int s3C, int s4C, float healthMultiplier, float interval) {
        this.ship1sCount = s1C;
        this.ship1fCount = s2C;
        this.ship2sCount = s3C;
        this.ship2fCount = s4C;
        this.healthMultiplier = healthMultiplier;
        this.interval = interval;
        random = new Random();
    }

}