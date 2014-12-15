package com.deeep.jam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Achievements;
import com.deeep.jam.classes.Assets;

/**
 * Created by scanevaro on 26/09/2014.
 */
public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;

    private Image titleImage;
    private TextButton playButton;
    private TextButton aboutButton;
    private TextButton leaderboardsButton;
    private TextButton achievementsButton;
    private TextButton loginButton;
    private TextButton quitButton;
    private Button soundButton;
    private Table table;

    private Music intro;

    public MainMenuScreen() {
        game = (Game) Gdx.app.getApplicationListener();

        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), game.getSpriteBatch());

        // set input processor
        Gdx.input.setInputProcessor(stage);

        prepareAudio();
        setWidgets();
        configureActors();
        addListeners();
        setLayout();

        if (!Game.MUTE)
            intro.play();
    }

    private void prepareAudio() {
        intro = Assets.getAssets().getMainMenuMusic();
        intro.setVolume(0.25f);
    }

    private void setWidgets() {
        playButton = new TextButton("Play", Assets.getAssets().getSkin());
        aboutButton = new TextButton("About", Assets.getAssets().getSkin());
        leaderboardsButton = new TextButton("Leaderboards", Assets.getAssets().getSkin());
        achievementsButton = new TextButton("Achievements", Assets.getAssets().getSkin());
        loginButton = new TextButton("Login G+", Assets.getAssets().getSkin());
        quitButton = new TextButton("Quit", Assets.getAssets().getSkin().get("defaultRed", TextButton.TextButtonStyle.class));

        soundButton = new Button(Assets.getAssets().getSkin().get("soundStyle", Button.ButtonStyle.class));

        titleImage = new Image(Assets.getAssets().getTitle());
    }

    private void configureActors() {
        soundButton.setSize(128, 128);
        soundButton.setPosition(0, 0);

        playButton.setSize(128, 128);
        aboutButton.setSize(192, 128);
        leaderboardsButton.setSize(128, 128);
        achievementsButton.setSize(128, 128);
        loginButton.setSize(128, 128);
        quitButton.setSize(128, 128);

        titleImage.setSize(256, 192);
    }

    private void addListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE) Assets.getAssets().getSelected().play();

                if (intro.isPlaying())
                    intro.stop();

                new Achievements(game);

                game.setScreen(new GameScreen());
            }
        });

        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE)
                    Assets.getAssets().getSelected().play();

                game.setScreen(new AboutScreen());
            }
        });
        leaderboardsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.getLeaderboardGPGS();
            }
        });
        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.getAchievementsGPGS();
            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.loginGPGS();
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE)
                    Assets.getAssets().getSelected().play();

                Gdx.app.exit();
            }
        });
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE) {
                    Game.MUTE = true;
                    if (intro.isPlaying())
                        intro.pause();
                } else {
                    Game.MUTE = false;
                    if (!intro.isPlaying())
                        intro.play();
                }

            }
        });
    }

    private void setLayout() {
        //create table with skin
        table = new Table(Assets.getAssets().getSkin());
        //set fill parent
        table.setFillParent(true);
        //add game title, align center, set pad, and add new row
        table.add(titleImage).width(800).height(200).colspan(5);
        table.align(Align.center);
        table.pad(15);
        table.row();
        //add button and align center
        table.add(playButton).align(Align.center).width(128).height(128).colspan(5);
        //add a pad of 10 pixels
        table.row().pad(10);
        table.add(aboutButton).align(Align.center).width(192).height(128).colspan(5);
        //add new row
        table.row();
        table.add();
        table.add(leaderboardsButton).width(128).height(128);
        table.add(achievementsButton).width(128).height(128);
        table.add(loginButton).width(128).height(128);
        table.add();
        table.row().pad(10);

        //debug table layout
//        table.debug();

        //add table to stage
        stage.addActor(table);

        stage.addActor(soundButton);

        quitButton.setPosition(Game.VIRTUAL_WIDTH - quitButton.getWidth(), 0);
        stage.addActor(quitButton);
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
        stage.getViewport().update(width, height, true);
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