package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deeep.jam.classes.Worlds;

/**
 * Created by scanevaro on 17/10/2014.
 */
public abstract class Enemy {
    public float x, y;
    public float force;
    public float rotation;
    private Sprite sprite;
    private Body body;
    BodyDef bodyDef = new BodyDef();
    PolygonShape groundShape;

    protected Enemy(float x, float y, float force, float rotation) {
        this.x = x;
        this.y = y;
        this.force = force;
        this.rotation = rotation;
    }

    public void update(float delta) {
        x += Math.cos(rotation) * delta * force;
        y += Math.sin(rotation) * delta * force;
        if (sprite != null) {
            sprite.setPosition(x, y);
            sprite.setRotation(rotation);
            if (body != null)
                body.setTransform(x, y, (float) (rotation));
        }
    }

    public void setSprite(TextureRegion textureRegion) {
        sprite = new Sprite(textureRegion);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getWidth() / 2);
        bodyDef.position.setAngleRad(rotation);
        bodyDef.position.set(0, 0);
        bodyDef.fixedRotation = false;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        groundShape = new PolygonShape();
        groundShape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;

        body = Worlds.world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);
    }

    public void draw(SpriteBatch spriteBatch) {
        if (sprite != null) {
            sprite.draw(spriteBatch);
        }
    }

    public boolean contains(int x, int y) {

        return false;
    }
}
