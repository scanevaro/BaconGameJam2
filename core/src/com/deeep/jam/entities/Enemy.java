package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deeep.jam.classes.Worlds;
import com.deeep.jam.math.PositionVector;

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
    private float health = 100;
    public float cX, cY;
    public boolean collide;

    protected Enemy(float x, float y, float force, float rotation) {
        this.x = x;
        this.y = y;
        this.force = force;
        this.rotation = rotation;
    }

    protected Enemy(PositionVector positionV, float force) {
        this.x = positionV.x;
        this.y = positionV.y;
        this.rotation = positionV.theta;
        this.force = force;
    }

    public void update(float delta) {
        if (Worlds.ship != null) {
            rotation = (float) Math.atan2(Worlds.ship.y - y, Worlds.ship.x - x);
        }
        x += Math.cos(rotation) * delta * force;
        y += Math.sin(rotation) * delta * force;
        if (sprite != null) {
            sprite.setPosition(x - sprite.getWidth() / 2, y - (sprite.getHeight() / 2));
            sprite.setRotation((float) Math.toDegrees(rotation - Math.PI / 2));
            if (body != null)
                body.setTransform(x, y, (float) (rotation - Math.PI / 2));
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

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
