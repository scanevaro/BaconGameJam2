package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

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
        Skin touchPadSkin = new Skin();
        touchPadSkin.add("touchBackground", new Texture(Gdx.files.internal("images/touchBackground.png")));
        touchPadSkin.add("touchKnob", new Texture(Gdx.files.internal("images/touchKnob.png")));

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.knob = touchPadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchPadSkin.getDrawable("touchBackground");

        movementPad = new Touchpad(10, touchpadStyle);
        firePad = new Touchpad(10, touchpadStyle);

        movementPad.setBounds(15, 15, 200, 200);
        firePad.setBounds(80, 15, 200, 200);
        movementVector = new Vector2(0, 0);
        fireVector = new Vector2(0, 0);

    }

    public void addToStage(Stage stage) {
        movementPad.setBounds(15, 15, 200, 200);
        firePad.setBounds(stage.getWidth()-215, 15, 200, 200);
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
