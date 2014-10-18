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
    private ImageButton shopButton;
    private Window shopDialog;
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

        ImageButton.ImageButtonStyle nowPlayingStyle = new ImageButton.ImageButtonStyle();
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

        healthLabel.setPosition(0, Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight() - moneyLabel.getPrefHeight() - healthLabel.getPrefHeight());

        health.setPosition(healthLabel.getPrefWidth(), Game.VIRTUAL_HEIGHT - waveLabel.getPrefHeight() - moneyLabel.getPrefHeight() - healthLabel.getPrefHeight());

        shopButton.setSize(64, 64);
        shopButton.setPosition(Game.VIRTUAL_WIDTH - shopButton.getWidth(), Game.VIRTUAL_HEIGHT - shopButton.getHeight());

        stage.addActor(waveLabel);
        stage.addActor(wave);
        stage.addActor(moneyLabel);
        stage.addActor(money);
        stage.addActor(healthLabel);
        stage.addActor(health);
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

//        ImageButton.ImageButtonStyle gunButtonStyle = new ImageButton.ImageButtonStyle();
//        gunButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getShopButton()));
//        ImageButton smallGunButton = new ImageButton(gunButtonStyle);
//        smallGunButton.setSize(32, 32);
//
//        shopDialog.row();
//        shopDialog.add(smallGunButton).width(32);
//        shopDialog.add(new TextButton("Buy", Assets.getAssets().getSkin())).width(32);
//
//        ImageButton.ImageButtonStyle mediumGunButtonStyle = new ImageButton.ImageButtonStyle();
//        mediumGunButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getShopButton()));
//        ImageButton mediumGunButton = new ImageButton(mediumGunButtonStyle);
//        mediumGunButton.setSize(32, 32);1
//
//        shopDialog.row();
//        shopDialog.add(mediumGunButton).width(32);
//        shopDialog.add(new TextButton("Buy", Assets.getAssets().getSkin())).width(32);
//
//        ImageButton.ImageButtonStyle bigGunButtonStyle = new ImageButton.ImageButtonStyle();
//        bigGunButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getShopButton()));
//        ImageButton bigGunButton = new ImageButton(bigGunButtonStyle);
//        bigGunButton.setSize(32, 32);
//
//        shopDialog.row();
//        shopDialog.add(bigGunButton).width(32);
//        shopDialog.add(new TextButton("Buy", Assets.getAssets().getSkin())).width(32);
//        shopDialog.pack();
//        shopDialog.pack();

        Table gunsTable = new Table(Assets.getAssets().getSkin());
        Table container = new Table(Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle smallGunButtonStyle = new ImageButton.ImageButtonStyle();
        smallGunButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
        ImageButton smallGunButton = new ImageButton(smallGunButtonStyle);
        smallGunButton.setSize(64, 64);

        ImageButton.ImageButtonStyle mediumGunButtonStyle = new ImageButton.ImageButtonStyle();
        mediumGunButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_dual_gray"));
        ImageButton mediumGunButton = new ImageButton(mediumGunButtonStyle);
        mediumGunButton.setSize(64, 64);

        ImageButton.ImageButtonStyle bigGunButtonStyle = new ImageButton.ImageButtonStyle();
        bigGunButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
        ImageButton bigGunButton = new ImageButton(bigGunButtonStyle);
        bigGunButton.setSize(64, 64);

        gunsTable.add(smallGunButton).width(64).height(64);
        gunsTable.row();
        gunsTable.add(mediumGunButton).width(64).height(64);
        gunsTable.row();
        gunsTable.add(bigGunButton).width(64).height(64);
        gunsTable.debug();

        container.add(gunsTable);
        container.setFillParent(true);

        ImageButton.ImageButtonStyle shipButtonStyle = new ImageButton.ImageButtonStyle();
        shipButtonStyle.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_large_body"));
        ImageButton shipButton = new ImageButton(shipButtonStyle);

        container.add(shipButton).width(200).height(350);
        container.debug();

        shopDialog.addActor(container);
        shopDialog.setSize(512, 512);

        shopDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2);
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