package com.deeep.jam.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.jam.classes.Assets;

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

        public Effect(int type, float x, float y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void update(float deltaT) {
            stateTime += deltaT;
        }
    }
}
