package com.deeep.jam.entities;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Worlds;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Elmar on 19-10-2014.
 */
public class Effects {
    private static Effects effects;
    private ArrayList<Effect> effectArrayList = new ArrayList<Effect>();
    private ArrayList<Effect> removeList = new ArrayList<Effect>();
    private HashMap<Integer, Animation> integerArrayHashMap = new HashMap<Integer, Animation>();

    public Effects() {
        for (int i = 0; i < 12; i++) {
            integerArrayHashMap.put(i, new Animation(.01f, Assets.getAssets().getTextureRegions("explosion/expl_0" + i)));
        }
    }

    public static Effects getEffects() {
        if (effects == null)
            effects = new Effects();
        return effects;
    }

    public void update(float deltaT) {
        for (Effect effect : effectArrayList) {
            effect.update(deltaT);
            if (integerArrayHashMap.get(effect.type).isAnimationFinished(effect.stateTime))
                removeList.add(effect);
        }
        for (Effect effect : removeList) {
            effect.pointLight.remove();
            effect.pointLight.dispose();
            effectArrayList.remove(effect);
        }
        removeList.clear();
    }

    public void render(SpriteBatch spriteBatch) {
        for (Effect effect : effectArrayList) {
            spriteBatch.draw(integerArrayHashMap.get(effect.type).getKeyFrame(effect.stateTime), effect.x, effect.y);
        }
    }

    public void addEffect(Effect effect) {
        effectArrayList.add(effect);
    }

    public static class Effect {
        public int type;
        float x, y;
        private float stateTime = 0;
        public PointLight pointLight;

        public Effect(int type, float x, float y) {
            this.type = type;
            this.x = x;
            this.y = y;
            pointLight = new PointLight(Worlds.rayHandler, 20, new Color(0.8f, 0.10f, 0.10f, 0.9f), 10, x, y);
        }

        public int getType() {
            return type;
        }

        public void update(float deltaT) {
            stateTime += deltaT;
        }
    }
}
