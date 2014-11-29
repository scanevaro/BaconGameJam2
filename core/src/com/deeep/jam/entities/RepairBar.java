package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.deeep.jam.classes.Assets;
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
        loadingBar = new NinePatchDrawable(Assets.getAssets().getSkin().getPatch("repairBarFill"));
        loadingBarBackground = new NinePatchDrawable(Assets.getAssets().getSkin().getPatch("healthBar"));

        stateTime = 0;
    }

    public void setMainActor(Ship ship) {
        this.ship = ship;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (stateTime > 0)
            loadingBar.draw(batch, getX() + 15, getY() + 22, stateTime / 10 * (getWidth() - 25) * getScaleX(), getHeight() * .3f * getScaleY());
        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
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