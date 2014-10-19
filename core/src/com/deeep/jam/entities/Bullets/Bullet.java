package com.deeep.jam.entities.Bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deeep.jam.classes.Map;
import com.deeep.jam.classes.Worlds;

/**
 * Created by Elmar on 19-10-2014.
 */
public abstract class Bullet {
    private Sprite sprite;
    private float rotation;
    private float force;
    private float x, y;
    private Body body;
    BodyDef bodyDef = new BodyDef();
    PolygonShape groundShape;
    private boolean alive = true;
    private float aliveTimer = 0;
    public float aliveTime = 10;

    public Bullet(Sprite sprite, float rotation, float force, float x, float y) {
        this.sprite = sprite;
        this.rotation = rotation;
        this.force = force;
        this.x = x;
        this.y = y;
        sprite.setRotation((float) Math.toDegrees(rotation + Math.PI / 2));

        bodyDef.position.setAngleRad(rotation);
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        groundShape = new PolygonShape();
        groundShape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;

        body = Worlds.world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);
    }

    public void update(float deltaT) {
        if (aliveTimer >= aliveTime) {
            alive = false;
        }
        aliveTimer += deltaT;
        x += deltaT * Math.cos(rotation) * force;
        y += deltaT * Math.sin(rotation) * force;
        sprite.setPosition(x, y);
        body.setTransform(x, y, (float) (rotation + Math.PI / 2));
    }

    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    public boolean isDead() {
        if (x < 0 || x > Map.sizeX || y < 0 || y > Map.sizeY || !alive)
            return true;
        return false;
    }
}
