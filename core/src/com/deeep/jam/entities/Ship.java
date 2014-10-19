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
import com.deeep.jam.entities.guns.BigCannon;
import com.deeep.jam.entities.guns.MegaCannon;
import com.deeep.jam.entities.guns.SmallCanon;
import com.deeep.jam.entities.guns.TwinCannon;

import java.util.ArrayList;
import java.util.HashMap;

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
    public final float maxForce = 4.5f;
    private Sprite sprite;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float splashTimer = 0;
    private Body body;
    BodyDef bodyDef = new BodyDef();
    PolygonShape groundShape;
    private Sprite[] splash;
    private ArrayList<Gun> guns = new ArrayList<Gun>();
    private HashMap<Integer, Gun> slotsToSmallGuns = new HashMap<Integer, Gun>();
    private HashMap<Integer, Gun> slotsToMediumGuns = new HashMap<Integer, Gun>();
    private HashMap<Integer, Gun> slotsToBigGuns = new HashMap<Integer, Gun>();
    private HashMap<Integer, Gun> slotsToTwinGuns = new HashMap<Integer, Gun>();

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
        for (int i = 0; i < 5; i++) {
            guns.add(new SmallCanon(i));
        }
        for (int i = 0; i < 4; i++) {
            guns.add(new BigCannon(i));
        }
        guns.add(new MegaCannon(0));
        guns.add(new TwinCannon(0));
    }

    public void updateSmallGun(int slot) {
        if (slotsToSmallGuns.containsKey(slot)) {
            slotsToSmallGuns.get(slot).levelUp();
        } else {
            SmallCanon smallCanon = new SmallCanon(slot);
            guns.add(smallCanon);
            slotsToSmallGuns.put(slot, smallCanon);
        }
    }

    public void updateMediumGun(int slot) {
        if (slotsToMediumGuns.containsKey(slot)) {
            slotsToMediumGuns.get(slot).levelUp();
        } else {
            BigCannon smallCanon = new BigCannon(slot);
            guns.add(smallCanon);
            slotsToMediumGuns.put(slot, smallCanon);
        }
    }

    public void updateBigGun(int slot) {
        if (slotsToBigGuns.containsKey(slot)) {
            slotsToBigGuns.get(slot).levelUp();
        } else {
            MegaCannon smallCanon = new MegaCannon(slot);
            guns.add(smallCanon);
            slotsToBigGuns.put(slot, smallCanon);
        }
    }

    public void updateTwinGun(int slot) {
        if (slotsToTwinGuns.containsKey(slot)) {
            slotsToTwinGuns.get(slot).levelUp();
        } else {
            TwinCannon smallCanon = new TwinCannon(slot);
            guns.add(smallCanon);
            slotsToTwinGuns.put(slot, smallCanon);
        }
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
            rotation += deltaT * (force / maxForce);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= deltaT * (force / maxForce);
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
        splashTimer = (splashTimer + force * deltaT * 4) % 5;
        splash[(int) splashTimer].setPosition(x - splash[(int) force].getWidth() / 2, y - splash[(int) force].getHeight() / 2);
        splash[(int) splashTimer].setRotation((float) Math.toDegrees(rotation - Math.PI / 2));
        body.setTransform(x, y, (float) (rotation + Math.PI / 2));
        for (Gun gun : guns) {
            gun.update(this, deltaT);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        splash[(int) splashTimer].draw(spriteBatch);
        sprite.setPosition(x - sprite.getWidth() / 2, y - (sprite.getHeight() / 2));
        sprite.setRotation((int) Math.toDegrees(rotation - Math.PI / 2));
        sprite.draw(spriteBatch);
        for (Gun gun : guns) {
            gun.render(spriteBatch);
        }
        spriteBatch.end();

    }


    public String toString() {
        return "rot force (x, y)" + rotation + " " + force + " (" + x + ", " + y + ")";
    }
}
