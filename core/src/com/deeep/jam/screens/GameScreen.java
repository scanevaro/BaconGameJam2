package com.deeep.jam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Worlds;
import com.deeep.jam.entities.HealthBar;


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
    private Label waveLabel;
    private Label wave;
    private Label moneyLabel;
    private Label money;
    private Label healthLabel;
    private Label health;
    private HealthBar healthBar;
    private ImageButton shopButton;
    private Window shopDialog;
    private ImageButton smallGunButton;
    private ImageButton mediumGunButton;
    private ImageButton bigGunButton;
    private ImageButton bigGunDualButton;
    private ImageButton bigGunBaseButton;
    private ImageButton baseGunDarkButton;
    //World
    private Worlds world;

    @Override
    public void show() {
        this.game = (Game) Gdx.app.getApplicationListener();

        prepareScreen();
        setWidgets();
        addListeners();
        setLayout();
        prepareWorld();

        prepareShop();
        addShopListeners();

        Gdx.input.setInputProcessor(stage);
    }

    private void prepareScreen() {
        camera = game.getCamera();
        camera.position.set(Game.VIRTUAL_WIDTH / 2, Game.VIRTUAL_HEIGHT / 2, 0);
        spriteBatch = game.getSpriteBatch();
        stage = new Stage(new StretchViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), spriteBatch);
    }

    private void setWidgets() {
        waveLabel = new Label("Wave: ", Assets.getAssets().getSkin());
        wave = new Label("0", Assets.getAssets().getSkin());
        moneyLabel = new Label("$: ", Assets.getAssets().getSkin());
        money = new Label("0", Assets.getAssets().getSkin());
        healthLabel = new Label("Health: ", Assets.getAssets().getSkin());
        health = new Label("5", Assets.getAssets().getSkin());
        healthBar = new HealthBar();

        ImageButton.ImageButtonStyle nowPlayingStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        nowPlayingStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getShopButton()));
        shopButton = new ImageButton(nowPlayingStyle);
    }

    private void addListeners() {
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addActor(shopDialog);
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

        stage.addActor(waveLabel);
        stage.addActor(wave);
        stage.addActor(moneyLabel);
        stage.addActor(money);
        stage.addActor(healthLabel);
        stage.addActor(health);
        stage.addActor(healthBar);
        stage.addActor(shopButton);
    }

    private void prepareWorld() {
        world = new Worlds();
    }

    private void prepareShop() {
        shopDialog = new Window("Shop - Blow sh*t up!", Assets.getAssets().getSkin());
        TextButton closeDiagog = new TextButton("X", Assets.getAssets().getSkin());
        closeDiagog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopDialog.remove();
            }
        });
        shopDialog.getButtonTable().add(closeDiagog).height(shopDialog.getPadTop());

        Table gunsLeftTable = new Table(Assets.getAssets().getSkin());
        Table container = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle smallGunButtonStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        smallGunButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        smallGunButton = new ImageButton(smallGunButtonStyle);
        smallGunButton.setSize(64, 64);

        ImageButton.ImageButtonStyle mediumGunButtonStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        mediumGunButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_dual_gray"));
        mediumGunButton = new ImageButton(mediumGunButtonStyle);
        mediumGunButton.setSize(64, 64);

        ImageButton.ImageButtonStyle bigGunButtonStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bigGunButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        bigGunButton = new ImageButton(bigGunButtonStyle);
        bigGunButton.setSize(64, 64);

        gunsLeftTable.add(smallGunButton).width(64).height(64);
        gunsLeftTable.row();
        gunsLeftTable.add(mediumGunButton).width(64).height(64);
        gunsLeftTable.row();
        gunsLeftTable.add(bigGunButton).width(64).height(64);
        gunsLeftTable.debug();

        container.add(gunsLeftTable);
        container.setFillParent(true);

        ImageButton.ImageButtonStyle shipButtonStyle = new ImageButton.ImageButtonStyle();
        shipButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_large_body"));
        ImageButton shipButton = new ImageButton(shipButtonStyle);

        container.add(shipButton).width(200).height(350);
        container.debug();

        shopDialog.addActor(container);
        shopDialog.setSize(512, 512);

        shopDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2);

        Table gunsRightTable = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle bigGunDualStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bigGunDualStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun_dual"));
        bigGunDualButton = new ImageButton(bigGunDualStyle);
        bigGunDualButton.setSize(64, 64);

        ImageButton.ImageButtonStyle bigGunBaseStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        bigGunBaseStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_base_big"));
        bigGunBaseButton = new ImageButton(bigGunBaseStyle);
        bigGunBaseButton.setSize(64, 64);

        ImageButton.ImageButtonStyle baseGunDarkStyle = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        baseGunDarkStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_base_dark"));
        baseGunDarkButton = new ImageButton(baseGunDarkStyle);
        baseGunDarkButton.setSize(64, 64);

        gunsRightTable.add(bigGunDualButton).width(64).height(64);
        gunsRightTable.row();
        gunsRightTable.add(bigGunBaseButton).width(64).height(64);
        gunsRightTable.row();
        gunsRightTable.add(baseGunDarkButton).width(64).height(64);
        gunsRightTable.debug();

        container.add(gunsRightTable);
    }

    private void addShopListeners() {
        smallGunButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                    }
                }.text("Buy Small Gun for $1000?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        mediumGunButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                    }
                }.text("Buy Medium Gun for $1000?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        bigGunButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                    }
                }.text("Buy Small Gun for $1000?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        bigGunDualButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                    }
                }.text("Buy Small Gun for $1000?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        bigGunBaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                    }
                }.text("Buy Small Gun for $1000?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });
        baseGunDarkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                    protected void result(Object object) {
                        //TODO process the input
                        System.out.println("Chosen: " + object);
                    }
                }.text("Buy Small Gun for $1000?")
                        .button("Yes", true)
                        .button("No", false)
                        .show(stage);
            }
        });

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