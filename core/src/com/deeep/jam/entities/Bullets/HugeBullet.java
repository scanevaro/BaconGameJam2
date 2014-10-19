package com.deeep.jam.entities.Bullets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Assets;

/**
 * Created by Elmar on 19-10-2014.
 */
public class HugeBullet extends Bullet {
    private Animation animation;
    private float animationTimer = 0;

    public HugeBullet(float rotation, float force, float x, float y, float damage) {
        super(new Sprite(Assets.getAssets().getRegion("ship_gun_bullet_huge")), rotation, force, x, y, damage);
        animation = new Animation(0.1f, Assets.getAssets().getTextureRegions("rocket_flame/rocket_1"));
        animation.setPlayMode(Animation.PlayMode.LOOP);
        explosionType = 1;
    }

    public void update(float deltaT) {
        animationTimer += deltaT;
        if (aliveTimer >= aliveTime) {
            alive = false;
        }
        aliveTimer += deltaT;
        x += deltaT * Math.cos(rotation) * force;
        y += deltaT * Math.sin(rotation) * force;
        body.setTransform(x, y, (float) (rotation + Math.PI / 2));
    }

    public void render(SpriteBatch spriteBatch) {
        float width = animation.getKeyFrame(0).getRegionWidth();
        float height = animation.getKeyFrame(0).getRegionHeight();
        spriteBatch.draw(animation.getKeyFrame(animationTimer, true), x + width, y, 0, 0, width, height, 1, 1, (float) Math.toDegrees(rotation + Math.PI / 2));
    }
}
