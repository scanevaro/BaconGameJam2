package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Map {
    private TextureRegion[] water;
    public static int sizeX = 2048;
    public static int sizeY = 2048;

    public Map() {
        water = new TextureRegion[2];
        water[0] = Assets.getAssets().getRegion("water0");
        water[0] = Assets.getAssets().getRegion("water1");
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (int x = 0; x < sizeX / water[0].getRegionWidth(); x++) {
            for (int y = 0; y < sizeX / water[0].getRegionHeight(); y++) {
                spriteBatch.draw(water[0], x * water[0].getRegionWidth(), y * water[0].getRegionHeight());
            }
        }
        spriteBatch.end();
    }
}
