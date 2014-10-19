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
    public float rotation, finalRotation;
    protected Sprite sprite;
    public Body body;
    BodyDef bodyDef = new BodyDef();
    PolygonShape groundShape;
    protected float health = 10;
    public float cX, cY, decayCounter = 255, damageCausal;
    public boolean collide, sinking, decaying;
    public TextureRegion deadSprite;

    protected Enemy(PositionVector positionV, float force, float hp, float damageCausal) {
        this.damageCausal = damageCausal;
        this.x = positionV.x;
        this.y = positionV.y;
        this.rotation = positionV.theta;
        this.force = force;
    }

    public void update(float delta) {
        if (decaying) {
            decayCounter -= delta * 100;
            if (decayCounter < 0) decayCounter = 0;
            force = 0;
        } else {
            if (Worlds.ship != null) {
                if (!sinking)
                    rotation = (float) Math.atan2(Worlds.ship.y - y, Worlds.ship.x - x);
            }
            if (sprite != null) {
                if (body != null)
                    body.setTransform(x, y, (float) (rotation - Math.PI / 2));
            }
            if (sinking) force -= delta * 10F;
            if (force < 0) force = 0;
        }
        x += Math.cos(rotation) * delta * force;
        y += Math.sin(rotation) * delta * force;
    }

    public void actuallyFuckingSetTheSprite(TextureRegion textureRegion) {
        sprite = new Sprite(textureRegion);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getWidth() / 2);
        sprite.setPosition(x - sprite.getWidth() / 2, y - (sprite.getHeight() / 2));
    }

    public void setBox2DProperties(TextureRegion textureRegion) {
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
            sprite.setPosition(x - sprite.getWidth() / 2, y - (sprite.getHeight() / 2));
            sprite.setRotation((int) Math.toDegrees(rotation - Math.PI / 2));
            if (decaying) {
                sprite.draw(spriteBatch, decayCounter / 255);
            } else sprite.draw(spriteBatch);
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

    public void takeDamage(float h) {
        setHealth(getHealth() - h);
        if (getHealth() <= 0) {
            if (sinking) return;
            sinking = true;
            die();
        }
    }

    protected void die() {
        actuallyFuckingSetTheSprite(deadSprite);
        finalRotation = (float) Math.toDegrees(rotation - Math.PI / 2);
        startDecay();
    }

    protected abstract void startDecay();

    public void setForce(float force) {
        this.force = force;
    }
}
