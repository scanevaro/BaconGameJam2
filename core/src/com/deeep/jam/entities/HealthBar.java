package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.deeep.jam.classes.Assets;

/**
 * Created by scanevaro on 18/10/2014.
 */
public class HealthBar extends Actor {

    private NinePatchDrawable loadingBarBackground;

    private NinePatchDrawable loadingBar;

    private Ship ship;

    public HealthBar() {
        loadingBar = new NinePatchDrawable(Assets.getAssets().getSkin().getPatch("healthBarFill"));
        loadingBarBackground = new NinePatchDrawable(Assets.getAssets().getSkin().getPatch("healthBar"));
    }

    public void setMainActor(Ship ship) {
        this.ship = ship;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        loadingBar.draw(batch, getX() + 15, getY() + 22, ship.health / 100 * (getWidth() - 25) * getScaleX(), getHeight() * 0.3f * getScaleY());
        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
    }
}