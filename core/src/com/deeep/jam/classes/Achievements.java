package com.deeep.jam.classes;

import com.deeep.jam.Game;

/**
 * Created by scanevaro on 14/12/2014.
 */
public class Achievements {
    private static Game game;

    public Achievements(Game game) {
        this.game = game;
    }

    public static boolean noHits;
    public static boolean dontShoot;
    public static boolean dontMove;

    public static void sayHello(boolean flag) {
        if (flag) game.actionResolver.unlockAchievementGPGS("CgkIi5Hn5-EIEAIQBg");
    }

    public static void dontShoot(boolean flag) {
        if (flag) game.actionResolver.unlockAchievementGPGS("CgkIi5Hn5-EIEAIQAw");
    }

    public static void noHits(boolean flag) {
        if (flag) game.actionResolver.unlockAchievementGPGS("CgkIi5Hn5-EIEAIQBA");
    }

    public static void wave10(boolean flag) {
        if (flag) game.actionResolver.unlockAchievementGPGS("CgkIi5Hn5-EIEAIQBQ");
    }

    public static void dontMove(boolean flag) {
        if (flag) game.actionResolver.unlockAchievementGPGS("CgkIi5Hn5-EIEAIQBw");
    }
}
