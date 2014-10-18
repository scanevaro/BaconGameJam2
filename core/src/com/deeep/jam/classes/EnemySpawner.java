package com.deeep.jam.classes;

import java.util.ArrayList;

/**
 * Created by Elmar on 18-10-2014.
 */
public class EnemySpawner {
    private ArrayList<Formations> formationses;

    public EnemySpawner() {
        formationses = new ArrayList<Formations>();
    }

    public void update(float deltaT) {
        if (!formationses.isEmpty()) {
            formationses.get(0).update(deltaT);
            if (formationses.get(0).isDone()) {
                formationses.remove(0);
            }
        }
    }

    public void addFormation(Formations formations) {
        formationses.add(formations);
    }

}
