package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.Camera;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Map {
    private TextureRegion[] water_1;
    public static int sizeX = 4096;
    public static int sizeY = 4096;
    private float waterTimer = 0;
    boolean water1 = false;

    public Map() {
        water_1 = new TextureRegion[15];
        for (int i = 0; i < 15; i++) {
            water_1[i] = Assets.getAssets().getRegion("WATER_ANIM", i + 20000);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        waterTimer += Gdx.graphics.getDeltaTime() * 14;
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            water1 = true;
        }
        spriteBatch.begin();
        for (int x = 0; x < sizeX / (water_1[0].getRegionWidth()); x++) {
            for (int y = 0; y < sizeY / (water_1[0].getRegionHeight()); y++) {
                if (Camera.getCamera().viewportContains(x * water_1[0].getRegionWidth() * 2 + 25, y * water_1[0].getRegionHeight() * 2 + 25)) {
                    spriteBatch.draw(water_1[(int) waterTimer % 14], x * water_1[0].getRegionWidth() * 2, y * water_1[0].getRegionHeight() * 2, water_1[0].getRegionHeight() * 2, water_1[0].getRegionHeight() * 2);
                }
            }
        }
        spriteBatch.end();
    }
}