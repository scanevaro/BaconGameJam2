package com.deeep.jam.classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Elmar on 11/10/2014.
 */
public class Controller {
    private static Controller controller;

    public static Controller getController() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    private Touchpad movementPad, firePad;
    private Vector2 movementVector, fireVector;

    public Controller() {
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.knob = new TextureRegionDrawable(Assets.getAssets().getRegion("touchKnob"));
        touchpadStyle.knob.setMinWidth(64);
        touchpadStyle.knob.setMinHeight(64);
        touchpadStyle.background = new TextureRegionDrawable(Assets.getAssets().getRegion("touchBackground"));
        touchpadStyle.background.setMinWidth(64);
        touchpadStyle.background.setMinHeight(64);

        movementPad = new Touchpad(10, touchpadStyle);
        firePad = new Touchpad(10, touchpadStyle);

        movementPad.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        firePad.setColor(0.5f, 0.5f, 0.5f, 0.5f);

        movementVector = new Vector2(0, 0);
        fireVector = new Vector2(0, 0);
    }

    public void addToStage(Stage stage) {
        movementPad.setBounds(15, 15, 300, 300);
        firePad.setBounds(stage.getWidth() - 315, 15, 300, 300);
        stage.addActor(movementPad);
        stage.addActor(firePad);
    }

    public Vector2 getMovementVector() {
        movementVector.set(movementPad.getKnobPercentX(), movementPad.getKnobPercentY());
        return movementVector;
    }

    public Vector2 getFireVector() {
        fireVector.set(firePad.getKnobPercentX(), firePad.getKnobPercentY());
        return fireVector;
    }
}