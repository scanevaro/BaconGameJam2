package com.deeep.jam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Worlds;
import com.deeep.jam.entities.HealthBar;


/**
 * Created by scanevaro on 10/10/2014.
 */
public class GameScreen implements Screen {
    //Main
    private Game game;
    //Screen
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Stage stage; //for UI
    private boolean dialogOpen;
    //Widgets
    private Label waveLabel;
    private Label wave;
    private Label moneyLabel;
    private Label money;
    private Label healthLabel;
    private Label health;
    private Label scoreLabel;
    private Label score;
    private HealthBar healthBar;
    private ImageButton shopButton;
    private Window shopDialog;
    private ImageButton smallSocket1Button;
    private ImageButton topGun2Button;
    private ImageButton topGun3Button;
    private ImageButton smallSocket2;
    private ImageButton leftGun2Button;
    private ImageButton leftGun3Button;
    private ImageButton rightGun1Button;
    private ImageButton rightGun2Button;
    private ImageButton rightGun3Button;
    private ImageButton muteButton;
    private ImageButton bottomGun1Button;
    private ImageButton bottomGun2Button;
    //World
    private Worlds world;
    //Sounds
    private Music inGameMusic;
    private Sound selectedSound;
    private Sound shopClicked;

    @Override
    public void show() {
        this.game = (Game) Gdx.app.getApplicationListener();

        prepareAudio();
        prepareScreen();
        setWidgets();
        addListeners();
        setLayout();
        prepareWorld();

        prepareShop();
        addShopListeners();

        Gdx.input.setInputProcessor(stage);

        if (!Game.MUTE)
            inGameMusic.play();
    }

    private void prepareAudio() {
        inGameMusic = Assets.getAssets().getInGameMusic();
        inGameMusic.setLooping(true);

        selectedSound = Assets.getAssets().getSelected();
        shopClicked = Assets.getAssets().getShopClick();
    }

    private void prepareScreen() {
        camera = Camera.getCamera().getOrthographicCamera();
        camera.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        spriteBatch = game.getSpriteBatch();
        stage = new Stage(new StretchViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), spriteBatch);
    }

    private void setWidgets() {
        waveLabel = new Label("Wave: ", Assets.getAssets().getSkin());
        wave = new Label("0", Assets.getAssets().getSkin());
        moneyLabel = new Label("CASH $ : ", Assets.getAssets().getSkin());
        money = new Label("0", Assets.getAssets().getSkin());
        healthLabel = new Label("Health: ", Assets.getAssets().getSkin());
        health = new Label("5", Assets.getAssets().getSkin());
        scoreLabel = new Label("Score: ", Assets.getAssets().getSkin());
        score = new Label("0", Assets.getAssets().getSkin());

        healthBar = new HealthBar();

        ImageButton.ImageButtonStyle nowPlayingStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        nowPlayingStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getShopButton()));
        shopButton = new ImageButton(nowPlayingStyle);

        ImageButton.ImageButtonStyle soundStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        soundStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getSoundIcon()));
        soundStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getMuteIcon()));
        muteButton = new ImageButton(soundStyle);

        stage.addActor(waveLabel);
        stage.addActor(wave);
        stage.addActor(moneyLabel);
        stage.addActor(money);
        stage.addActor(healthLabel);
        stage.addActor(health);
        stage.addActor(healthBar);
        stage.addActor(shopButton);
        stage.addActor(scoreLabel);
        stage.addActor(score);
        stage.addActor(muteButton);
    }

    private void addListeners() {
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE)
                    selectedSound.play();
                dialogOpen = true;
                stage.addActor(shopDialog);
            }
        });
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE) {
                    Game.MUTE = true;
                    if (inGameMusic.isPlaying())
                        inGameMusic.pause();
                } else {
                    Game.MUTE = false;
                    if (!inGameMusic.isPlaying())
                        inGameMusic.play();
                }
            }
        });
    }

    private void setLayout() {
        waveLabel.setPosition(0, Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight());

        wave.setPosition(waveLabel.getPrefWidth(), Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight());

        moneyLabel.setPosition(0, Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight() - moneyLabel.getPrefHeight());

        money.setPosition(moneyLabel.getPrefWidth(), Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight() - moneyLabel.getPrefHeight());

        healthBar.setSize(128, 32);
        healthBar.setPosition(Game.VIRTUAL_WIDTH / 2 - healthBar.getWidth() / 2, 0);

        healthLabel.setPosition(Game.VIRTUAL_WIDTH / 2 - healthBar.getWidth() / 2, healthBar.getHeight());

        shopButton.setSize(64, 64);
        shopButton.setPosition(Game.VIRTUAL_WIDTH - shopButton.getWidth(), Game.VIRTUAL_HEIGHT - shopButton.getHeight());

        scoreLabel.setPosition(0, Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight() - moneyLabel.getPrefHeight() - scoreLabel.getPrefHeight());
        score.setPosition(scoreLabel.getPrefWidth(), Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight() - moneyLabel.getPrefHeight() - scoreLabel.getPrefHeight());

        muteButton.setSize(64, 64);
        muteButton.setPosition(Game.VIRTUAL_WIDTH - muteButton.getWidth() - shopButton.getWidth(), Game.VIRTUAL_HEIGHT - muteButton.getHeight());
        if (Game.MUTE)
            muteButton.setChecked(true);
    }

    private void prepareWorld() {
        world = new Worlds();
    }

    private void prepareShop() {
        shopDialog = new Window("Shop - Blow sht up !", Assets.getAssets().getSkin());
        TextButton closeDiagog = new TextButton("X", Assets.getAssets().getSkin());
        closeDiagog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Game.MUTE)
                    selectedSound.play();
                dialogOpen = false;
                shopDialog.remove();
            }
        });
        shopDialog.getButtonTable().add(closeDiagog).height(shopDialog.getPadTop());

        Table container = new Table(Assets.getAssets().getSkin());

        Table topGunsTable = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle topGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        topGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallSocket1Button = new ImageButton(topGun1Style);
        smallSocket1Button.setSize(64, 64);

        ImageButton.ImageButtonStyle topGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        topGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_dual_gray"));
        topGun2Button = new ImageButton(topGun2Style);
        topGun2Button.setSize(64, 64);

        ImageButton.ImageButtonStyle topGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        topGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        topGun3Button = new ImageButton(topGun3Style);
        topGun3Button.setSize(64, 64);

        topGunsTable.add(smallSocket1Button).width(64).height(64);
        topGunsTable.row();
        topGunsTable.add(topGun2Button).width(64).height(64);
//        topGunsTable.add(topGun3Button).width(64).height(64);

        container.add();
        container.add(topGunsTable).align(Align.bottom);
        container.row();

        Table leftGunsTable = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle leftGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        leftGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallSocket2 = new ImageButton(leftGun1Style);
        smallSocket2.setSize(64, 64);

        ImageButton.ImageButtonStyle leftGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        leftGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_dual_gray"));
        leftGun2Button = new ImageButton(leftGun2Style);
        leftGun2Button.setSize(64, 64);

        ImageButton.ImageButtonStyle leftGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        leftGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        leftGun3Button = new ImageButton(leftGun3Style);
        leftGun3Button.setSize(64, 64);

        leftGunsTable.add(smallSocket2).padBottom(40).width(64).height(64);
        leftGunsTable.row();
        leftGunsTable.add(leftGun2Button).width(64).height(64);
        leftGunsTable.row();
        leftGunsTable.add(leftGun3Button).width(64).height(64);

        container.add(leftGunsTable);
        container.setFillParent(true);

        shopDialog.setSize(512, 512);

        ImageButton.ImageButtonStyle shipButtonStyle = new ImageButton.ImageButtonStyle();
        shipButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getCanonIndexReferenceTexture()));
        ImageButton shipButton = new ImageButton(shipButtonStyle);
        shipButton.setPosition(shopDialog.getWidth() / 2 - shipButton.getWidth() / 2, shopDialog.getHeight() / 2 - shipButton.getHeight() / 2);
        shopDialog.addActor(shipButton);

        shopDialog.addActor(container);

        shopDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2);

        Table rightGunsTable = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle rightGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        rightGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun_dual"));
        rightGun1Button = new ImageButton(rightGun1Style);
        rightGun1Button.setSize(64, 64);

        ImageButton.ImageButtonStyle rightGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        rightGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_base_big"));
        rightGun2Button = new ImageButton(rightGun2Style);
        rightGun2Button.setSize(64, 64);

        ImageButton.ImageButtonStyle rightGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        rightGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_base_dark"));
        rightGun3Button = new ImageButton(rightGun3Style);
        rightGun3Button.setSize(64, 64);

        rightGunsTable.add(rightGun1Button).padBottom(40).width(64).height(64);
        rightGunsTable.row();
        rightGunsTable.add(rightGun2Button).width(64).height(64);
        rightGunsTable.row();
        rightGunsTable.add(rightGun3Button).width(64).height(64);

        Table middleGunsTable = new Table(Assets.getAssets().getSkin());

        middleGunsTable.add(topGun3Button).width(64).height(64);
        middleGunsTable.row();

        container.add(middleGunsTable);
        container.add(rightGunsTable);

        Table bottomGunsTable = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle bottomGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bottomGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun_dual"));
        bottomGun1Button = new ImageButton(bottomGun1Style);
        bottomGun1Button.setSize(64, 64);

        ImageButton.ImageButtonStyle bottomGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bottomGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_base_big"));
        bottomGun2Button = new ImageButton(bottomGun2Style);
        bottomGun2Button.setSize(64, 64);

//        bottomGunsTable.add(bottomGun1Button).width(64).height(64);
        middleGunsTable.add(bottomGun1Button).padTop(40).width(64).height(64);
        middleGunsTable.row();
        middleGunsTable.add(bottomGun2Button).width(64).height(64);
//        bottomGunsTable.add(bottomGun2Button).width(64).height(64);

//        container.row();
//        container.add();
//        container.add(bottomGunsTable).align(Align.center);

//        container.debug();
//        topGunsTable.debug();
//        leftGunsTable.debug();
//        rightGunsTable.debug();
//        bottomGunsTable.debug();
    }

    private void addShopListeners() {
        smallSocket1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if ((Boolean) object)
                            world.ship.updateSmallGun(1);

                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Top Gun 1 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        topGun2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if ((Boolean) object)
                            world.ship.updateMediumGun(1);

                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Top Gun 2 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        topGun3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Top Gun 3 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        smallSocket2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Left Gun 1 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        leftGun2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Left Gun 2 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        leftGun3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Left Gun 3 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        rightGun1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Right Gun 1 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        rightGun2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Right Gun 2 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        rightGun3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Right Gun 3 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        bottomGun1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Bottom Gun 1 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        bottomGun2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Bottom Gun 1 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
    }

    @Override
    public void render(float delta) {
        if (!dialogOpen)
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