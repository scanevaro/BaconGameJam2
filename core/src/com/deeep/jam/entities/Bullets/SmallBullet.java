package com.deeep.jam.entities.Bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.deeep.jam.classes.Assets;

/**
 * Created by Elmar on 19-10-2014.
 */
public class SmallBullet extends Bullet {
    public SmallBullet(float rotation, float force, float x, float y) {
        super(new Sprite(Assets.getAssets().getRegion("ship_gun_gray_bullet")), rotation, force, x, y);
    }
}
