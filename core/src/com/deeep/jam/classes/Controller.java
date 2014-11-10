package com.deeep.jam.classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.Vector;

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
        touchPadSkin.add("touchBackground", Assets.getAssets().getRegion("touchBackground").getTexture());
        touchPadSkin.add("touchKnob", Assets.getAssets().getRegion("touchKnob").getTexture());
        System.out.println("assets: "+Assets.getAssets().getRegion("touchKnob"));
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
       stage.addActor(movementPad);
       stage.addActor(firePad);
    }

    public Vector2 getMovementVector() {
       // movementVector.set(movementPad.getKnobPercentX(), movementPad.getKnobPercentY());
        return movementVector;
    }

    public Vector2 getFireVector() {
       // fireVector.set(firePad.getKnobPercentX(), firePad.getKnobPercentY());
        return fireVector;
    }

}
