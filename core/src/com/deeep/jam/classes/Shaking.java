package com.deeep.jam.classes;

import com.badlogic.gdx.graphics.Camera;
import com.deeep.jam.Game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Elmar on 18-10-2014.
 */
public class Shaking {
    private Camera camera;
    private ArrayList<Shake> shakes = new ArrayList<Shake>();
    private ArrayList<Shake> remove = new ArrayList<Shake>();
    private Random random = new Random();

    public Shaking(Camera camera) {
        this.camera = camera;
    }

    public void update(float deltaT) {
        if (!shakes.isEmpty()) {
            float magnitude = 0;
            for (Shake shake : shakes) {
                shake.update(deltaT);
                if (shake.timer >= shake.duration) {
                    remove.add(shake);
                }
                magnitude += shake.magnitude;
            }
            for (Shake shake : remove) {
                shakes.remove(shake);
            }
            remove.clear();
            camera.position.set((Game.VIRTUAL_WIDTH / 2) + (0.5f - random.nextFloat()) * magnitude, (Game.VIRTUAL_HEIGHT / 2) + (0.5f - random.nextFloat()) * magnitude, 0);
        }

    }

    public void addShake(Shake shake) {
        shakes.add(shake);
    }

    public static class Shake {
        public float duration, magnitude;
        public float timer;

        public Shake(float duration, float magnitude) {
            this.duration = duration;
            this.magnitude = magnitude;
        }

        public void update(float deltaT) {
            timer += deltaT;
        }
    }
}
