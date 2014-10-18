package com.deeep.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Map;
import com.deeep.jam.classes.Worlds;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Ship {
    private TextureRegion textureRegion;
    public float x, y;
    public float rotation;
    public float force;
    public final float acceleration = 1f;
    public final float friction = acceleration / 2;
    public final float maxForce = 5;
    private Sprite sprite;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Body body;
    BodyDef bodyDef = new BodyDef();
    PolygonShape groundShape;
    private Sprite[] splash;

    public Ship() {
        splash = new Sprite[5];
        for (int i = 0; i < 5; i++) {
            splash[i] = new Sprite(Assets.getAssets().getRegion("water_ripple_big", i));
        }
        x = Map.sizeX / 2;
        y = Map.sizeY / 2;
        force = rotation = 0;
        sprite = new Sprite(Assets.getAssets().getRegion("ship_large_body"));
        sprite.setCenterX(sprite.getWidth() / 2);
        sprite.setCenterY(sprite.getHeight() / 2);
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
        if (force > deltaT * friction) {
            force -= deltaT * friction;
        } else if (force < -deltaT * friction) {
            force += deltaT * friction;
        } else {
            force = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += deltaT * (force / maxForce) / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= deltaT * (force / maxForce) / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (force < maxForce) {
                force += deltaT * acceleration;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (force > maxForce / 5) {
                force -= deltaT * acceleration / 2;
            }
        }
        x += Math.cos(rotation) * force;
        y += Math.sin(rotation) * force;
        //System.out.println(this);
        splash[(int) force].setPosition(x - splash[(int) force].getWidth() / 2, y - splash[(int) force].getHeight() / 2);
        splash[(int) force].setRotation((float) Math.toDegrees(rotation - Math.PI / 2));
        body.setTransform(x, y, (float) (rotation + Math.PI / 2));
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        splash[(int) force].draw(spriteBatch);
        sprite.setPosition(x - sprite.getWidth() / 2, y - (sprite.getHeight() / 2));
        sprite.setRotation((float) Math.toDegrees(rotation - Math.PI / 2));
        sprite.draw(spriteBatch);
        spriteBatch.end();

    }


    public String toString() {
        return "rot force (x, y)" + rotation + " " + force + " (" + x + ", " + y + ")";
    }
}
