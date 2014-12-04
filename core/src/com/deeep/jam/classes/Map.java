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
    private TextureRegion[] water_2;
    public static int sizeX = 4096;
    public static int sizeY = 4096;
    private float waterTimer = 0;
    boolean water1 = false;

    public Map() {
        water_1 = new TextureRegion[11];
        water_2 = new TextureRegion[11];
        for (int i = 0; i < 11; i++) {
            water_1[i] = Assets.getAssets().getRegion("water_anim000" + i);
            water_2[i] = Assets.getAssets().getRegion("water_anim2000" + i);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        waterTimer += Gdx.graphics.getDeltaTime() * 10;
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            water1 = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            water1 = false;
        }
        spriteBatch.begin();
        for (int x = 0; x < sizeX / water_1[0].getRegionWidth(); x++) {
            for (int y = 0; y < sizeX / water_1[0].getRegionHeight(); y++) {
                if (Camera.getCamera().viewportContains(x * water_1[0].getRegionWidth(), y * water_1[0].getRegionHeight())) {
                    if (water1) {
                        spriteBatch.draw(water_2[(int) waterTimer % 10], x * water_1[0].getRegionWidth(), y * water_1[0].getRegionHeight());
                    } else {
                        spriteBatch.draw(water_1[(int) waterTimer % 10], x * water_1[0].getRegionWidth(), y * water_1[0].getRegionHeight());
                    }
                }
            }
        }
        spriteBatch.end();
    }
}