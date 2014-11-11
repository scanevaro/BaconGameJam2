package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.deeep.jam.screens.GameScreen;

/**
 * Created by scanevaro on 10/11/2014.
 */
public class RepairBar extends Actor {

    private NinePatchDrawable loadingBarBackground;

    private NinePatchDrawable loadingBar;

    private Ship ship;

    public float stateTime;

    public RepairBar() {
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("data/uiskin.atlas"));
        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 5, 5, 4, 4);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);

        stateTime = 0;
    }

    public void setMainActor(Ship ship) {
        this.ship = ship;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        if (stateTime > 0)
            loadingBar.draw(batch, getX(), getY(), stateTime / 10 * getWidth() * getScaleX(), getHeight() * getScaleY());
    }

    @Override
    public void act(float delta) {
        if (ship.health != 100 && !GameScreen.dialogOpen) {
            stateTime += delta;

            if (stateTime >= 10) {
                stateTime = 0;

                if (ship.health <= 90)
                    ship.health += 10;
            }
        }
    }
}