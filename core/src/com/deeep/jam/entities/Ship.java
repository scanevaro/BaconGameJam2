package com.deeep.jam.entities;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Controller;
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
    private boolean outsideScreen = false;
    private TextureRegion textureRegion;
    public float x, y;
    public float rotation;
    public float force;
    public final float acceleration = 1f;
    public final float friction = acceleration / 2;
    public final float maxForce = 4.5f;
    private Sprite sprite;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public float splashTimer = 0, health = 100;
    private Body body;
    BodyDef bodyDef = new BodyDef();
    PolygonShape groundShape;
    private Sprite[] splash;
    private ArrayList<Gun> guns = new ArrayList<Gun>();
    private HashMap<Integer, Gun> slotsToSmallGuns = new HashMap<Integer, Gun>();
    private HashMap<Integer, Gun> slotsToMediumGuns = new HashMap<Integer, Gun>();
    private HashMap<Integer, Gun> slotsToBigGuns = new HashMap<Integer, Gun>();
    private HashMap<Integer, Gun> slotsToTwinGuns = new HashMap<Integer, Gun>();
    private PointLight pointLight;

    private RepairBar repairBar;

    public Ship() {

        splash = new Sprite[5];
        for (int i = 0; i < 5; i++) {
            splash[i] = new Sprite(Assets.getAssets().getRegion("water_ripple_big", i));
        }
        x = 10;
        //x = Map.sizeX / 2;
        y = Map.sizeY / 2;
        pointLight = new PointLight(Worlds.rayHandler, 100, Color.WHITE, 500, x, y);
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
        pointLight.attachToBody(body);
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

    public Gun getSmallCanon(int slot) {
        if (slotsToSmallGuns.containsKey(slot)) {
            return slotsToSmallGuns.get(slot);
        } else return new SmallCanon(slot);
    }

    public Gun getMediumGun(int slot) {
        if (slotsToMediumGuns.containsKey(slot)) {
            return slotsToMediumGuns.get(slot);
        } else return new BigCannon(slot);
    }

    public Gun getBigGun(int slot) {
        if (slotsToBigGuns.containsKey(slot)) {
            return slotsToBigGuns.get(slot);
        } else return new MegaCannon(slot);
    }

    public Gun getTwinGuns(int slot) {
        if (slotsToTwinGuns.containsKey(slot)) {
            return slotsToTwinGuns.get(slot);
        } else return new TwinCannon(slot);
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
        pointLight.update();
        if (force > deltaT * friction) {
            force -= deltaT * friction;
        } else if (force < -deltaT * friction) {
            force += deltaT * friction;
        } else {
            force = 0;
        }
        if (Game.android) {
            if (Controller.getController().getMovementVector().len() != 0) {
                if (force < maxForce) {
                    force += deltaT * acceleration * Math.abs(Controller.getController().getMovementVector().len());
                }
                double inputRotation = Controller.getController().getMovementVector().angleRad() + Math.PI;
                double difference = (inputRotation - rotation + Math.PI) - Math.PI * 2;
                difference %= (Math.PI * 2);
                if (difference > 0) {
                    if (Math.abs(difference) >= Math.PI) {
                        rotation -= deltaT * (force / maxForce) * Math.min(1, Math.abs(difference));
                    } else {
                        rotation += deltaT * (force / maxForce) * Math.min(1, Math.abs(difference));
                    }
                } else {
                    if (Math.abs(difference) >= Math.PI) {
                        rotation += deltaT * (force / maxForce) * Math.min(1, Math.abs(difference));
                    } else {
                        rotation -= deltaT * (force / maxForce) * Math.min(1, Math.abs(difference));
                    }
                }
                rotation %= (Math.PI * 2);
            }
        } else {
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
        }
        x += Math.cos(rotation) * force;
        y += Math.sin(rotation) * force;
        //System.out.println(this);
        splashTimer = (splashTimer + force * deltaT * 4) % 5;
        splash[(int) splashTimer].setPosition(x - splash[(int) force].getWidth() / 2, y - splash[(int) force].getHeight() / 2);
        splash[(int) splashTimer].setRotation((float) Math.toDegrees(rotation - Math.PI / 2));
        body.setTransform(x, y, (float) (rotation + Math.PI / 2));
        for (Gun gun : guns) {
            if (Game.android) {
                if (Worlds.isDay()) {
                    if (Controller.getController().getFireVector().len() != 0) {
                        gun.fire();
                        gun.setRotation((int) Controller.getController().getFireVector().angle());
                    }
                } else if (!Worlds.isGettingDay()) {
                    Worlds.setDay(true);
                }
            }
            gun.update(this, deltaT);
        }
        if(x <0 || y <0 || x > Map.sizeX || y > Map.sizeY){
            outsideScreen = true;
        }else{
            outsideScreen = false;
        }
        Worlds.setOutsideOfScreen(outsideScreen);

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

    public void takeDamage(float damageCausal) {
        Game.score--;
        health -= damageCausal;
        if (health <= 0) {
            die();
        }

        repairBar.stateTime = 0;
    }

    private void die() {
        Game.GAME_OVER = true;

        System.out.println("Game Over!");
    }

    public void setRepairBar(RepairBar repairBar) {
        this.repairBar = repairBar;
    }
}