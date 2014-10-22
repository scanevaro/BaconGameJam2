package com.deeep.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.deeep.jam.classes.Map;
import com.deeep.jam.classes.Shaking;
import com.deeep.jam.entities.Ship;


/**
 * Created by Elmar on 19-10-2014.
 */
public class Camera {
    public static final float VIRTUAL_WIDTH = 1024;
    public static final float VIRTUAL_HEIGHT = 720;
    private final int offSet = 100;
    private static Camera camera;
    private OrthographicCamera orthographicCamera;
    private Ship ship;
    private Shaking shaking;
    private Rectangle viewBounds;

    public static Camera getCamera() {
        if (camera == null)
            camera = new Camera();
        return camera;
    }

    public Camera() {
        orthographicCamera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        orthographicCamera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        shaking = new Shaking(orthographicCamera);
        viewBounds = new Rectangle(0, 0, (int) VIRTUAL_WIDTH + offSet * 2, (int) VIRTUAL_HEIGHT + offSet * 2);
    }

    public boolean viewportContains(int x, int y) {
        return viewBounds.contains(x, y);
    }

    public Matrix4 getProjectionMatrix() {
        return orthographicCamera.combined;
    }

    public void followShip(Ship ship) {
        this.ship = ship;
    }

    public void update(float deltaT) {
        orthographicCamera.update();
        shaking.update(deltaT);
        viewBounds.setPosition((int) (orthographicCamera.position.x - offSet - VIRTUAL_WIDTH / 2), (int) (orthographicCamera.position.y - offSet - VIRTUAL_HEIGHT / 2));
        if (ship != null) {
            orthographicCamera.position.set((int) (Math.min(Math.max((int) ship.x, Game.VIRTUAL_WIDTH / 2), Map.sizeX - Game.VIRTUAL_WIDTH) + shaking.getShakeX()),
                    (int) (Math.min(Math.max((int) ship.y, Game.VIRTUAL_HEIGHT / 2), Map.sizeY - Game.VIRTUAL_HEIGHT) + shaking.getShakeY()),
                    0);
        } else {

        }
    }

    public Shaking getShaking() {
        return shaking;
    }

    public OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }

    public float getTouchX() {
        return Gdx.input.getX() * (Game.VIRTUAL_WIDTH / Gdx.graphics.getWidth());
    }

    public float getTouchY() {
        return Gdx.input.getY() * (Game.VIRTUAL_HEIGHT / Gdx.graphics.getHeight());
    }
}
