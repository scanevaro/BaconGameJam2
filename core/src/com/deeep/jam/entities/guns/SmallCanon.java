package com.deeep.jam.entities.guns;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.deeep.jam.entities.Gun;

/**
 * Created by Andreas on 10/18/2014.
 */
public class SmallCanon extends Gun {

    TextureRegion[][] guns = new TextureRegion[3][2];

    public SmallCanon(int socketId) {
        switch (socketId) {
            case 0:
                offX = 30F;
                offX = -20F;
            case 1:
                offX = 5F;
                offX = -200F;
            case 2:
                offX = 105F;
                offX = -200F;
            case 3:
                offX = 5F;
                offX = -220F;
            case 4:
                offX = 105F;
                offX = 220F;
        }
    }

    public void update() {

    }

}
