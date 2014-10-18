package com.deeep.jam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deeep.jam.Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "BaconGameJam - Millons of Trolls";
        config.width = (int) Game.VIRTUAL_WIDTH;
        config.height = (int) Game.VIRTUAL_HEIGHT;
        new LwjglApplication(new Game(), config);
    }
}
