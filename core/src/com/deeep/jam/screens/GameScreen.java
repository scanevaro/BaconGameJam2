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
    public static int money_amount = 5000;

    //Main
    private Game game;
    //Screen
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Stage stage; //for UI
    private boolean dialogOpen;
    //Widgets
    private Label waveLabel;
    public static Label wave;
    private Label moneyLabel;
    public static Label money;
    private Label healthLabel;
    private Label health;
    private Label scoreLabel;
    private Label score;
    private HealthBar healthBar;
    private ImageButton shopButton;
    private Window shopDialog;
    private ImageButton smallGun1Button;
    private ImageButton bigGun1Button;
    private ImageButton dualGun1Button;
    private ImageButton mediumGun1Button;
    private ImageButton smallGun2Button;
    private ImageButton smallGun3Button;
    private ImageButton mediumGun2Button;
    private ImageButton smallGun4Button;
    private ImageButton smallGun5Button;
    private ImageButton muteButton;
    private ImageButton mediumGun3Button;
    private ImageButton mediumGun4Button;
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
        inGameMusic.setVolume(0.5f);

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
        money = new Label(money_amount + "", Assets.getAssets().getSkin());
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
        healthBar.setMainActor(world.ship);
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
        smallGun1Button = new ImageButton(topGun1Style);
        smallGun1Button.setSize(64, 64);

        ImageButton.ImageButtonStyle topGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        topGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_huge"));
        bigGun1Button = new ImageButton(topGun2Style);
        bigGun1Button.setSize(64, 64);

        ImageButton.ImageButtonStyle topGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        topGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_dual_gray"));
        dualGun1Button = new ImageButton(topGun3Style);
        dualGun1Button.setSize(64, 64);

        topGunsTable.add(smallGun1Button).width(64).height(64);
        topGunsTable.row();
        topGunsTable.add(bigGun1Button).width(64).height(64);

        container.add();
        container.add(topGunsTable).align(Align.bottom);
        container.row();

        Table leftGunsTable = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle leftGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        leftGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        mediumGun1Button = new ImageButton(leftGun1Style);
        mediumGun1Button.setSize(64, 64);

        ImageButton.ImageButtonStyle leftGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        leftGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallGun2Button = new ImageButton(leftGun2Style);
        smallGun2Button.setSize(64, 64);

        ImageButton.ImageButtonStyle leftGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        leftGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallGun3Button = new ImageButton(leftGun3Style);
        smallGun3Button.setSize(64, 64);

        leftGunsTable.add(mediumGun1Button).padBottom(40).width(64).height(64);
        leftGunsTable.row();
        leftGunsTable.add(smallGun2Button).width(64).height(64);
        leftGunsTable.row();
        leftGunsTable.add(smallGun3Button).width(64).height(64);

        container.add(leftGunsTable);
        container.setFillParent(true);
        //( ͡° ͜ʖ ͡°) < l'elmar face
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
        rightGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        mediumGun2Button = new ImageButton(rightGun1Style);
        mediumGun2Button.setSize(64, 64);

        ImageButton.ImageButtonStyle rightGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        rightGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallGun4Button = new ImageButton(rightGun2Style);
        smallGun4Button.setSize(64, 64);

        ImageButton.ImageButtonStyle rightGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        rightGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallGun5Button = new ImageButton(rightGun3Style);
        smallGun5Button.setSize(64, 64);

        rightGunsTable.add(mediumGun2Button).padBottom(40).width(64).height(64);
        rightGunsTable.row();
        rightGunsTable.add(smallGun4Button).width(64).height(64);
        rightGunsTable.row();
        rightGunsTable.add(smallGun5Button).width(64).height(64);

        Table middleGunsTable = new Table(Assets.getAssets().getSkin());

        middleGunsTable.add(dualGun1Button).width(64).height(64);
        middleGunsTable.row();

        container.add(middleGunsTable);
        container.add(rightGunsTable);

        ImageButton.ImageButtonStyle bottomGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bottomGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        mediumGun3Button = new ImageButton(bottomGun1Style);
        mediumGun3Button.setSize(64, 64);

        ImageButton.ImageButtonStyle bottomGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bottomGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        mediumGun4Button = new ImageButton(bottomGun2Style);
        mediumGun4Button.setSize(64, 64);

        middleGunsTable.add(mediumGun3Button).padTop(40).width(64).height(64);
        middleGunsTable.row();
        middleGunsTable.add(mediumGun4Button).width(64).height(64);

//        container.debug();
//        topGunsTable.debug();
//        leftGunsTable.debug();
//        rightGunsTable.debug();
//        bottomGunsTable.debug();
    }


    private void addShopListeners() {
        smallGun1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Small Gun 1 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        bigGun1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Big Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 2000) {
                            if ((Boolean) object)
                                world.ship.updateBigGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 2000;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Big Gun 1 for 2000 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        dualGun1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Dual Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 1500) {
                            if ((Boolean) object)
                                world.ship.updateTwinGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1500;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Dual Gun 1 for 1500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        mediumGun1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }

                        if ((Boolean) object)
                            world.ship.updateMediumGun(0);
                        if (!Game.MUTE)
                            shopClicked.play();
                    }
                }.text("Upgrade Medium Gun 1 for 1000 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        smallGun2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(1);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Small Gun 2 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        smallGun3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(2);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Small Gun 3 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        mediumGun2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(1);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Medium Gun 2 for 1000 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        smallGun4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(3);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Small Gun 4 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        smallGun5Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(4);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Small Gun 5 for 500 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        mediumGun3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(2);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Medium Gun 3 for 1000 moneyz?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        mediumGun4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(3);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                    }
                }.text("Upgrade Medium Gun 4 for 1000 moneyz?")
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

        if (Game.GAME_OVER)
            prepareGameOverDialog();

        stage.act();

        world.draw();
        stage.draw();
    }

    private void prepareGameOverDialog() {
        dialogOpen = true;
        Game.GAME_OVER = false;

        Window gameOverDialog = new Window("REKT !", Assets.getAssets().getSkin());

        //( ͡° ͜ʖ ͡°) < l'elmar face
        gameOverDialog.setSize(512, 512);

        ImageButton.ImageButtonStyle shipButtonStyle = new ImageButton.ImageButtonStyle();
        shipButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getGameOverTexture()));
        ImageButton gameOverButton = new ImageButton(shipButtonStyle);
        gameOverButton.setSize(500, 500);
        gameOverButton.setPosition(gameOverDialog.getWidth() / 2 - gameOverButton.getWidth() / 2, gameOverDialog.getHeight() / 2 - gameOverButton.getHeight() / 2);

        //add to dialog
        gameOverDialog.addActor(gameOverButton);

        TextButton retryButton = new TextButton("R e t r y", Assets.getAssets().getSkin());
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialogOpen = false;
                game.setScreen(new GameScreen());
            }
        });
        retryButton.setPosition(gameOverDialog.getWidth() / 2 - retryButton.getWidth() / 2, gameOverDialog.getHeight() / 2 - 100);

        //add to dialog
        gameOverDialog.addActor(retryButton);

        TextButton quitButton = new TextButton("I M  D O N E", Assets.getAssets().getSkin());
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        quitButton.setPosition(gameOverDialog.getWidth() / 2 - quitButton.getWidth() / 2, gameOverDialog.getHeight() / 2 - 135);

        //add to dialog
        gameOverDialog.addActor(quitButton);

        gameOverDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2);

        stage.addActor(gameOverDialog);
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