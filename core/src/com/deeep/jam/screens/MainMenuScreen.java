package com.deeep.jam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;

/**
 * Created by scanevaro on 26/09/2014.
 */
public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;

    private TextButton playButton;
    private TextButton joinButton;
    private TextButton spectateButton;

    public MainMenuScreen() {
        game = (Game) Gdx.app.getApplicationListener();

        stage = new Stage(new StretchViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), game.getSpriteBatch());

        // set input processor
        Gdx.input.setInputProcessor(stage);

        setActors();
        configureActors();
        addListeners();
        setLayout();
    }

    private void setActors() {
        playButton = new TextButton("Play", Assets.getAssets().getSkin());
        joinButton = new TextButton("About", Assets.getAssets().getSkin());
        spectateButton = new TextButton("Quit", Assets.getAssets().getSkin());
    }

    private void configureActors() {
    }

    private void addListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });

        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        spectateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
    }

    private void setLayout() {
        //create table with skin
        Table table = new Table(Assets.getAssets().getSkin());
        //set fill parent
        table.setFillParent(true);
        //add game title, align center, set pad, and add new row
        table.add(new Image(Assets.getAssets().getTitle()));
        table.align(Align.center);
        table.pad(15);
        table.row();
        //add button and align center
        table.add(playButton).align(Align.center);
        //add a pad of 10 pixels
        table.row().pad(10);
        table.add(joinButton).align(Align.center);
        //add new row
        table.row();
        table.add(spectateButton).align(Align.center);
        table.row().pad(10);

        //add table actor to stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}