package com.deeep.jam.entities.Bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.deeep.jam.classes.Assets;

/**
 * Created by Elmar on 19-10-2014.
 */
public class BigBullet extends Bullet {
    public BigBullet(float rotation, float force, float x, float y) {
        super(new Sprite(Assets.getAssets().getRegion("ship_gun_bullet_big")), rotation, force, x, y);
        explosionType = 3;
    }

}
