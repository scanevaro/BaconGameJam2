package com.deeep.jam.entities.guns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Shaking;
import com.deeep.jam.entities.Bullets.Bullet;
import com.deeep.jam.entities.Bullets.SmallBullet;
import com.deeep.jam.entities.Gun;
import com.deeep.jam.entities.Ship;

/**
 * Created by Andreas on 10/18/2014.
 */
public class SmallCanon extends Gun {
    private float shootTimer = 0;
    private float fireRate = 0.1f;
    Sprite[][] guns = new Sprite[3][2];

    public SmallCanon(int socketId) {
        guns[0][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray"));
        guns[0][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_gray_destroyed"));
        guns[1][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_green"));
        guns[1][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_green_destroyed"));
        guns[2][0] = new Sprite(Assets.getAssets().getRegion("ship_gun_red"));
        guns[2][1] = new Sprite(Assets.getAssets().getRegion("ship_gun_red_destroyed"));
        switch (socketId) {
            case 0:
                offX = 0;
                offY = 90;
                break;
            case 1:
                offX = -45;
                offY = -45;
                break;
            case 2:
                offX = 45;
                offY = -45;
                break;
            case 3:
                offX = -45;
                offY = -75;
                break;
            case 4:
                offX = 45;
                offY = -75;
                break;
        }
    }

    //Function powered by: ( ͡° ͜ʖ ͡°) < l'elmar face

    @Override
    public void update(Ship ship, float deltaT) {
        if (shootTimer < fireRate) {
            shootTimer += deltaT;
        }

        this.x = (float) ((float) (ship.x) + offX * Math.cos(ship.rotation - Math.PI/2) - offY * Math.sin(ship.rotation- Math.PI/2));
        this.y = (float) ((float) (ship.y) + offX * Math.sin(ship.rotation- Math.PI/2) + offY * Math.cos(ship.rotation- Math.PI/2));
        float deltaX = (Gdx.input.getX()) - (x - Camera.getCamera().getOrthographicCamera().position.x + Game.VIRTUAL_WIDTH / 2);
        float deltaY = (Gdx.graphics.getHeight()) - Gdx.input.getY() - (y - Camera.getCamera().getOrthographicCamera().position.y + Game.VIRTUAL_HEIGHT / 2);
        //guns[level][(damaged) ? 1 : 0].setOrigin(guns[level][(damaged) ? 1 : 0].getWidth() / 2, guns[level][(damaged) ? 1 : 0].getHeight() / 2);
        //guns[level][(damaged) ? 1 : 0].setOrigin(guns[level][(damaged) ? 1 : 0].getWidth() / 2, guns[level][(damaged) ? 1 : 0].getHeight() / 2);
        guns[level][(damaged) ? 1 : 0].setPosition((x) -guns[level][(damaged) ? 1 : 0].getWidth()/2 , (y)-guns[level][(damaged) ? 1 : 0].getHeight()/2);
        theta = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
        if (Gdx.input.isTouched()) {
            if (shootTimer >= fireRate) {
                shootTimer = 0;
                Camera.getCamera().getShaking().addShake(new Shaking.Shake(0.1f, 1f));
                bullets.add(new SmallBullet((float) Math.toRadians(theta), 150, x, y));
            }
        }
        for (Bullet bullet : bullets) {
            bullet.update(deltaT);
        }
        guns[level][(damaged) ? 1 : 0].setRotation(theta + 90);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (Bullet bullet : bullets) {
            bullet.render(spriteBatch);
        }
        guns[level][(damaged) ? 1 : 0].draw(spriteBatch);
    }
}
