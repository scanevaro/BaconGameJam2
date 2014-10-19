package com.deeep.jam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private TextButton aboutButton;
    private TextButton quitButton;

    private Music intro;
    private Sound sound;

    public MainMenuScreen() {
        game = (Game) Gdx.app.getApplicationListener();

        stage = new Stage(new StretchViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), game.getSpriteBatch());

        // set input processor
        Gdx.input.setInputProcessor(stage);

        prepareAudio();
        setActors();
        configureActors();
        addListeners();
        setLayout();

        intro.play();
    }

    private void prepareAudio() {
        intro = Assets.getAssets().getMainMenuMusic();
    }

    private void setActors() {
        playButton = new TextButton("Play", Assets.getAssets().getSkin());
        aboutButton = new TextButton("About", Assets.getAssets().getSkin());
        quitButton = new TextButton("Quit", Assets.getAssets().getSkin());
    }

    private void configureActors() {
    }

    private void addListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.getAssets().getSelected().play();

                if (intro.isPlaying())
                    intro.stop();

                game.setScreen(new GameScreen());
            }
        });

        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.getAssets().getSelected().play();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.getAssets().getSelected().play();
                Gdx.app.exit();
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
        table.add(aboutButton).align(Align.center);
        //add new row
        table.row();
        table.add(quitButton).align(Align.center);
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