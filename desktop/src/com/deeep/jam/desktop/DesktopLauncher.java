package com.deeep.jam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deeep.jam.Game;
import com.deeep.jam.desktop.classes.ActionResolverDesktop;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Battle In The Atlantic";
        config.width = (int) Game.VIRTUAL_WIDTH;
        config.height = (int) Game.VIRTUAL_HEIGHT;
        new LwjglApplication(new Game(new ActionResolverDesktop(), false), config);
    }
}
