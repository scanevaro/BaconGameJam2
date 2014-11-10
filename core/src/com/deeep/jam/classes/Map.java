package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.Camera;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Map {
    private TextureRegion[] water;
    public static int sizeX = 4096;
    public static int sizeY = 4096;
    private float waterTimer = 0;

    public Map() {
        water = new TextureRegion[2];
        water[0] = Assets.getAssets().getRegion("water0");
        water[1] = Assets.getAssets().getRegion("water1");
        System.out.println("water:"+water[0]);
        System.out.println("water:"+water[1]);
    }

    public void render(SpriteBatch spriteBatch) {
        waterTimer += Gdx.graphics.getDeltaTime();

        spriteBatch.begin();
        for (int x = 0; x < sizeX / water[0].getRegionWidth(); x++) {
            for (int y = 0; y < sizeX / water[0].getRegionHeight(); y++) {
                if (Camera.getCamera().viewportContains(x * water[0].getRegionWidth(), y * water[0].getRegionHeight())) {
                    if (waterTimer < 1)
                        spriteBatch.draw(water[0], x * water[0].getRegionWidth(), y * water[0].getRegionHeight());
                    else if (waterTimer < 2)
                        spriteBatch.draw(water[1], x * water[1].getRegionWidth(), y * water[1].getRegionHeight());
                    else
                        waterTimer = 0;
                }
            }
        }
        spriteBatch.end();
    }
}
