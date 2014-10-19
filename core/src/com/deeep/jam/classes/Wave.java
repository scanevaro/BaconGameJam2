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
    public static final Wave WAVE_0 = new Wave(15, 4, 0, 0, 1.0F, 2F);
    public static final Wave WAVE_1 = new Wave(20, 8, 2, 0, 1.2F, 1.9F);
    public static final Wave WAVE_2 = new Wave(20, 8, 4, 2, 1.4F, 1.8F);
    public static final Wave WAVE_3 = new Wave(20, 8, 2, 0, 1.6F, 1.7F);
    public static final Wave WAVE_4 = new Wave(25, 10, 2, 5, 1.8F, 1.6F);
    public static final Wave WAVE_5 = new Wave(0, 25, 2, 0, 2.0F, 1.5F);
    public static final Wave WAVE_6 = new Wave(5, 5, 15, 0, 2.2F, 1.4F);
    public static final Wave WAVE_7 = new Wave(30, 10, 0, 0, 2.4F, 1.3F);
    public static final Wave WAVE_8 = new Wave(15, 15, 5, 5, 2.6F, 1.2F);
    public static final Wave WAVE_9 = new Wave(15, 0, 0, 0, 5.0F, 1.1F);
    public static final Wave WAVE_10 = new Wave(50, 0, 0, 0, 3.0F, 1.0F);
    public static final Wave WAVE_11 = new Wave(0, 20, 0, 0, 3.2F, 0.9F);
    public static final Wave WAVE_12 = new Wave(0, 0, 15, 0, 3.4F, 0.8F);
    public static final Wave WAVE_13 = new Wave(30, 16, 4, 4, 3.6F, 0.7F);
    public static final Wave WAVE_14 = new Wave(10, 20, 30, 40, 3.8F, 0.6F);
    public static final Wave WAVE_15 = new Wave(25, 0, 25, 0, 4.0F, 0.5F);
    public static final Wave WAVE_16 = new Wave(20, 25, 2, 25, 4.2F, 0.5F);
    public static final Wave WAVE_17 = new Wave(25, 25, 25, 25, 4.4F, 0.5F);
    public static final Wave WAVE_18 = new Wave(50, 50, 50, 50, 4.6F, 0.5F);
    public static final Wave WAVE_19 = new Wave(100, 100, 100, 100, 4.8F, 0.5F);
    public static final Wave WAVE_20 = new Wave(200, 200, 200, 200, 5.0F, 0.5F);

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