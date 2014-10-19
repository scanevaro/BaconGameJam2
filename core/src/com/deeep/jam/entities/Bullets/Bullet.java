package com.deeep.jam.entities.Bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Map;

/**
 * Created by Elmar on 19-10-2014.
 */
public abstract class Bullet {
    private Sprite sprite;
    private float rotation;
    private float force;
    private float x, y;

    public Bullet(Sprite sprite, float rotation, float force, float x, float y) {
        this.sprite = sprite;
        this.rotation = rotation;
        this.force = force;
        this.x = x;
        this.y = y;
        sprite.setRotation((float) Math.toDegrees(rotation + Math.PI/2));
    }

    public void update(float deltaT) {
        x += deltaT * Math.cos(rotation) * force;
        y += deltaT * Math.sin(rotation) * force;
        sprite.setPosition(x, y);
    }

    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    public boolean isDead() {
        if (x < 0 || x > Map.sizeX || y < 0 || y > Map.sizeY)
            return true;
        return false;
    }
}
