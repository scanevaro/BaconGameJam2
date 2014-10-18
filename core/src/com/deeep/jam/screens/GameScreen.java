package com.deeep.jam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.deeep.jam.Game;
import com.deeep.jam.classes.GameInputProcessor;
import com.deeep.jam.classes.World;

/**
 * Created by scanevaro on 10/10/2014.
 */
public class GameScreen implements Screen {
    private Game game;
    //Screen
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Stage stage; //for UI
    //Widgets

    //World
    private World world;

    @Override
    public void show() {
        this.game = (Game) Gdx.app.getApplicationListener();

        prepareScreen();
        setWidgets();
        setLayout();
        prepareWorld();

        Gdx.input.setInputProcessor(new GameInputProcessor());
    }

    private void prepareScreen() {
        camera = game.getCamera();
        camera.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        spriteBatch = game.getSpriteBatch();
        stage = new Stage(new StretchViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_WIDTH), spriteBatch);
    }

    private void setWidgets() {
    }

    private void setLayout() {
    }

    private void prepareWorld() {
        world = new World(true);
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        stage.act();

        world.draw();
        stage.draw();
    }


    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}