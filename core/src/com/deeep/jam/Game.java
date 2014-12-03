package com.deeep.jam;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.interfaces.ActionResolver;
import com.deeep.jam.screens.MainMenuScreen;

public class Game implements ApplicationListener {
    public static boolean android = false;
    public static final boolean DEBUG = true;
    public static final float VIRTUAL_WIDTH = 1024;
    public static final float VIRTUAL_HEIGHT = 720;
    private final float VIRTUAL_ASPECT = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;
    public static boolean GAME_OVER = false;
    public static boolean MUTE = false;
    public static int score = 0;
    public static Vector2 crop = new Vector2(0f, 0f);

    public ActionResolver actionResolver;
    private SpriteBatch spriteBatch;
    private Screen screen;
    public static Rectangle viewport;

    public Game(ActionResolver actionResolver, boolean android) {
        this.actionResolver = actionResolver;
        Game.android = android;
    }

    @Override
    public void create() {
        Assets.getAssets().load();

        spriteBatch = new SpriteBatch();

        viewport = new Rectangle();

        setScreen(new MainMenuScreen());
    }

    @Override
    public void render() {
        Camera.getCamera().update(Gdx.graphics.getDeltaTime());

//        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

        spriteBatch.setProjectionMatrix(Camera.getCamera().getProjectionMatrix());

        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);

//        float aspectRatio = (float) width / (float) height;
//        float scale = 1;
//
//        if (aspectRatio > VIRTUAL_ASPECT) {
//            scale = (float) height / VIRTUAL_HEIGHT;
//            crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
//        } else if (aspectRatio < VIRTUAL_ASPECT) {
//            scale = (float) width / VIRTUAL_WIDTH;
//            crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
//        } else {
//            scale = (float) width / VIRTUAL_WIDTH;
//        }
//
//        float w = VIRTUAL_WIDTH * scale;
//        float h = VIRTUAL_HEIGHT * scale;
//
//        viewport.set(crop.x, crop.y, w, h);
//
//        screen.resize((int) w, (int) h);
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
    }

    @Override
    public void dispose() {
        Assets.getAssets().dispose();
        if (screen != null) screen.dispose();
    }

    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}