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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.jam.Camera;
import com.deeep.jam.Game;
import com.deeep.jam.classes.Assets;
import com.deeep.jam.classes.Controller;
import com.deeep.jam.classes.Worlds;
import com.deeep.jam.entities.HealthBar;
import com.deeep.jam.entities.RepairBar;


/**
 * Created by scanevaro on 10/10/2014.
 */
public class GameScreen implements Screen {
    public static int money_amount = 100000;

    //Main
    private Game game;
    //Screen
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Stage stage; //for UI
    public static boolean dialogOpen;
    //Widgets
    private Label waveLabel;
    public static Label wave;
    private Label moneyLabel;
    public static Label money;
    private Label healthLabel;
    private Label repairLabel;
    private Label health;
    private Label scoreLabel;
    private Label score;
    private HealthBar healthBar;
    private RepairBar repairBar;
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
    private ImageButton shipImage;
    private Table container;
    private Table leftGunsTable;
    private Table rightGunsTable;
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
        Controller.getController().addToStage(stage);

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
        stage = new Stage(new FitViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), spriteBatch);
    }

    private void setWidgets() {
        waveLabel = new Label("Wave: ", Assets.getAssets().getSkin());
        wave = new Label("0", Assets.getAssets().getSkin());
        moneyLabel = new Label("CASH $ : ", Assets.getAssets().getSkin());
        money = new Label(money_amount + "", Assets.getAssets().getSkin());
        healthLabel = new Label("Health: ", Assets.getAssets().getSkin());
        repairLabel = new Label("Repairs... ", Assets.getAssets().getSkin());
        health = new Label("5", Assets.getAssets().getSkin());
        scoreLabel = new Label("Score: ", Assets.getAssets().getSkin());
        score = new Label("0", Assets.getAssets().getSkin());

        healthBar = new HealthBar();
        repairBar = new RepairBar();

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
        stage.addActor(repairLabel);
        stage.addActor(health);
        stage.addActor(healthBar);
        stage.addActor(repairBar);
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
                money.setText(money_amount + "");
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

        repairBar.setSize(96, 16);
        repairBar.setPosition(Game.VIRTUAL_WIDTH / 2 - repairBar.getWidth() / 2, 50);

        repairLabel.setPosition(Game.VIRTUAL_WIDTH / 2 - repairBar.getWidth() / 2, repairBar.getHeight() + 50);

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
        repairBar.setMainActor(world.ship);
        world.ship.setRepairBar(repairBar);
    }

    private void prepareShop() {
        {//initialize containers
            container = new Table(Assets.getAssets().getSkin());
            leftGunsTable = new Table(Assets.getAssets().getSkin());
            rightGunsTable = new Table(Assets.getAssets().getSkin());
        }

        {//create dialog, add close button, add table container. Its not added to the Stage until its wanted to be shown
            Window.WindowStyle style = new Window.WindowStyle();
            style.background = Assets.getAssets().getSkin().getDrawable("dialogTest");
            style.titleFont = Assets.getAssets().getSkin().getFont("gameFont");

            shopDialog = new Window("Shop - Blow sht up !", style);
//            shopDialog = new Window("Shop - Blow sht up !", Assets.getAssets().getSkin());
            shopDialog.setSize(512, 512);
            shopDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2);

            TextButton closeDialogButton = new TextButton("X", Assets.getAssets().getSkin());
            closeDialogButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!Game.MUTE)
                        selectedSound.play();
                    dialogOpen = false;
                    shopDialog.remove();
                }
            });
            shopDialog.getButtonTable().add(closeDialogButton).height(shopDialog.getPadTop());

            shopDialog.addActor(container);

            TextButton closeDialogButton2 = new TextButton("Close", Assets.getAssets().getSkin());
            closeDialogButton2.setSize(64, 64);
            closeDialogButton2.setPosition(shopDialog.getWidth() / 2 - closeDialogButton2.getWidth() / 2, 0);
            closeDialogButton2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!Game.MUTE)
                        selectedSound.play();
                    dialogOpen = false;
                    shopDialog.remove();
                }
            });
            shopDialog.add();
            shopDialog.addActor(closeDialogButton2);
        }

        {//setWidgets
            ImageButton.ImageButtonStyle topGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            topGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
            smallGun1Button = new ImageButton(topGun1Style);

            ImageButton.ImageButtonStyle leftGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            leftGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
            smallGun2Button = new ImageButton(leftGun2Style);

            ImageButton.ImageButtonStyle leftGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            leftGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
            smallGun3Button = new ImageButton(leftGun3Style);

            ImageButton.ImageButtonStyle rightGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            rightGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
            smallGun4Button = new ImageButton(rightGun2Style);

            ImageButton.ImageButtonStyle rightGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            rightGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_gray"));
            smallGun5Button = new ImageButton(rightGun3Style);

            ImageButton.ImageButtonStyle topGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            topGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_huge"));
            bigGun1Button = new ImageButton(topGun2Style);

            ImageButton.ImageButtonStyle topGun3Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            topGun3Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_gun_dual_gray"));
            dualGun1Button = new ImageButton(topGun3Style);

            ImageButton.ImageButtonStyle leftGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            leftGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
            mediumGun1Button = new ImageButton(leftGun1Style);

            ImageButton.ImageButtonStyle rightGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            rightGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
            mediumGun2Button = new ImageButton(rightGun1Style);

            ImageButton.ImageButtonStyle bottomGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            bottomGun1Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
            mediumGun3Button = new ImageButton(bottomGun1Style);

            ImageButton.ImageButtonStyle bottomGun2Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
            bottomGun2Style.imageUp = new TextureRegionDrawable(Assets.getAssets().getRegion("ship_big_gun"));
            mediumGun4Button = new ImageButton(bottomGun2Style);

            ImageButton.ImageButtonStyle shipButtonStyle = new ImageButton.ImageButtonStyle();
            shipButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getCanonIndexReferenceTexture()));
            shipImage = new ImageButton(shipButtonStyle);
        }

        {//configure widgets
            smallGun1Button.setSize(64, 64);
            smallGun2Button.setSize(64, 64);
            smallGun3Button.setSize(64, 64);
            smallGun4Button.setSize(64, 64);
            smallGun5Button.setSize(64, 64);

            bigGun1Button.setSize(64, 64);

            dualGun1Button.setSize(64, 64);

            mediumGun1Button.setSize(64, 64);
            mediumGun2Button.setSize(64, 64);
            mediumGun3Button.setSize(64, 64);
            mediumGun4Button.setSize(64, 64);

            shipImage.setPosition(shopDialog.getWidth() / 2 - shipImage.getWidth() / 2, shopDialog.getHeight() / 2 - shipImage.getHeight() / 2);
        }

        {//setLayout
            leftGunsTable.add(smallGun1Button).padBottom(2).width(64).height(64);
            leftGunsTable.row();
            leftGunsTable.add(bigGun1Button).padBottom(2).width(64).height(64);
            leftGunsTable.row();
            leftGunsTable.add(mediumGun1Button).padBottom(2).width(64).height(64);
            leftGunsTable.row();
            leftGunsTable.add(mediumGun3Button).padBottom(2).width(64).height(64);
            leftGunsTable.row();
            leftGunsTable.add(smallGun2Button).padBottom(2).width(64).height(64);
            leftGunsTable.row();
            leftGunsTable.add(smallGun3Button).width(64).height(64);

            rightGunsTable.add(mediumGun2Button).padTop(70).padBottom(2).width(64).height(64);
            rightGunsTable.row();
            rightGunsTable.add(dualGun1Button).padBottom(2).width(64).height(64);
            rightGunsTable.row();
            rightGunsTable.add(smallGun4Button).padBottom(2).width(64).height(64);
            rightGunsTable.row();
            rightGunsTable.add(smallGun5Button).padBottom(2).width(64).height(64);
            rightGunsTable.row();
            rightGunsTable.add(mediumGun4Button).width(64).height(64);

            container.add(leftGunsTable);
            container.add(shipImage).size(335, 420);
            container.add(rightGunsTable);
            container.setFillParent(true);
        }

        //( ͡° ͜ʖ ͡°) < l'elmar face

        {//debug layout
//            container.debug();
//            leftGunsTable.debug();
//            rightGunsTable.debug();
        }
    }


    private void addShopListeners() {
        TextButton yes1Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes1Button.setSize(64, 32);
        TextButton yes2Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes2Button.setSize(64, 32);
        TextButton yes3Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes3Button.setSize(64, 32);
        TextButton yes4Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes4Button.setSize(64, 32);
        TextButton yes5Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes5Button.setSize(64, 32);
        TextButton yes6Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes6Button.setSize(64, 32);
        TextButton yes7Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes7Button.setSize(64, 32);
        TextButton yes8Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes8Button.setSize(64, 32);
        TextButton yes9Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes9Button.setSize(64, 32);
        TextButton yes10Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes10Button.setSize(64, 32);
        TextButton yes11Button = new TextButton("Yes", Assets.getAssets().getSkin());
        yes11Button.setSize(64, 32);

        TextButton no1Button = new TextButton("No", Assets.getAssets().getSkin());
        no1Button.setSize(64, 32);
        TextButton no2Button = new TextButton("No", Assets.getAssets().getSkin());
        no2Button.setSize(64, 32);
        TextButton no3Button = new TextButton("No", Assets.getAssets().getSkin());
        no3Button.setSize(64, 32);
        TextButton no4Button = new TextButton("No", Assets.getAssets().getSkin());
        no4Button.setSize(64, 32);
        TextButton no5Button = new TextButton("No", Assets.getAssets().getSkin());
        no5Button.setSize(64, 32);
        TextButton no6Button = new TextButton("No", Assets.getAssets().getSkin());
        no6Button.setSize(64, 32);
        TextButton no7Button = new TextButton("No", Assets.getAssets().getSkin());
        no7Button.setSize(64, 32);
        TextButton no8Button = new TextButton("No", Assets.getAssets().getSkin());
        no8Button.setSize(64, 32);
        TextButton no9Button = new TextButton("No", Assets.getAssets().getSkin());
        no9Button.setSize(64, 32);
        TextButton no10Button = new TextButton("No", Assets.getAssets().getSkin());
        no10Button.setSize(64, 32);
        TextButton no11Button = new TextButton("No", Assets.getAssets().getSkin());
        no11Button.setSize(64, 32);

        final Dialog smallGun1Dialog;
        final Dialog smallGun2Dialog;
        final Dialog smallGun3Dialog;
        final Dialog smallGun4Dialog;
        final Dialog smallGun5Dialog;
        final Dialog bigGun1Dialog;
        final Dialog dualGun1Dialog;
        final Dialog mediumGun1Dialog;
        final Dialog mediumGun2Dialog;
        final Dialog mediumGun3Dialog;
        final Dialog mediumGun4Dialog;

        {//initialize dialogs
            smallGun1Dialog = new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                }
            };
            smallGun1Dialog.text("Upgrade Small Gun 1 for 500 moneyz?");
            smallGun1Dialog.getButtonTable().add(yes1Button).width(64).height(32);
            smallGun1Dialog.getButtonTable().add(no1Button).width(64).height(32);
            smallGun1Dialog.setObject(yes1Button, true);
            smallGun1Dialog.setObject(no1Button, false);

            bigGun1Dialog = new Dialog("Big Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 2000) {
                            if ((Boolean) object)
                                world.ship.updateBigGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 2000;
                            money.setText(money_amount + "");
                        }
                }
            };
            bigGun1Dialog.text("Upgrade Big Gun 1 for 2000 moneyz?");
            bigGun1Dialog.getButtonTable().add(yes2Button).width(64).height(32);
            bigGun1Dialog.getButtonTable().add(no2Button).width(64).height(32);
            bigGun1Dialog.setObject(yes2Button, true);
            bigGun1Dialog.setObject(no2Button, false);

            dualGun1Dialog = new Dialog("Dual Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 1500) {

                            world.ship.updateTwinGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1500;
                            money.setText(money_amount + "");
                        }
                }
            };
            dualGun1Dialog.text("Upgrade Dual Gun 1 for 1500 moneyz?");
            dualGun1Dialog.getButtonTable().add(yes3Button).width(64).height(32);
            dualGun1Dialog.getButtonTable().add(no3Button).width(64).height(32);
            dualGun1Dialog.setObject(yes3Button, true);
            dualGun1Dialog.setObject(no3Button, false);

            mediumGun1Dialog = new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(0);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                }
            };
            mediumGun1Dialog.text("Upgrade Medium Gun 1 for 1000 moneyz?");
            mediumGun1Dialog.getButtonTable().add(yes4Button).width(64).height(32);
            mediumGun1Dialog.getButtonTable().add(no4Button).width(64).height(32);
            mediumGun1Dialog.setObject(yes4Button, true);
            mediumGun1Dialog.setObject(no4Button, false);

            smallGun2Dialog = new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(1);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                }
            };
            smallGun2Dialog.text("Upgrade Small Gun 2 for 500 moneyz?");
            smallGun2Dialog.getButtonTable().add(yes5Button).width(64).height(32);
            smallGun2Dialog.getButtonTable().add(no5Button).width(64).height(32);
            smallGun2Dialog.setObject(yes5Button, true);
            smallGun2Dialog.setObject(no5Button, false);

            smallGun3Dialog = new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(2);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                }
            };
            smallGun3Dialog.text("Upgrade Small Gun 3 for 500 moneyz?");
            smallGun3Dialog.getButtonTable().add(yes6Button).width(64).height(32);
            smallGun3Dialog.getButtonTable().add(no6Button).width(64).height(32);
            smallGun3Dialog.setObject(yes6Button, true);
            smallGun3Dialog.setObject(no6Button, false);

            mediumGun2Dialog = new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(1);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                }
            };
            mediumGun2Dialog.text("Upgrade Medium Gun 2 for 1000 moneyz?");
            mediumGun2Dialog.getButtonTable().add(yes7Button).width(64).height(32);
            mediumGun2Dialog.getButtonTable().add(no7Button).width(64).height(32);
            mediumGun2Dialog.setObject(yes7Button, true);
            mediumGun2Dialog.setObject(no7Button, false);

            smallGun4Dialog = new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(3);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                }
            };
            smallGun4Dialog.text("Upgrade Small Gun 4 for 500 moneyz?");
            smallGun4Dialog.getButtonTable().add(yes8Button).width(64).height(32);
            smallGun4Dialog.getButtonTable().add(no8Button).width(64).height(32);
            smallGun4Dialog.setObject(yes8Button, true);
            smallGun4Dialog.setObject(no8Button, false);

            smallGun5Dialog = new Dialog("Small Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 500) {
                            if ((Boolean) object)
                                world.ship.updateSmallGun(4);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 500;
                            money.setText(money_amount + "");
                        }
                }
            };
            smallGun5Dialog.text("Upgrade Small Gun 5 for 500 moneyz?");
            smallGun5Dialog.getButtonTable().add(yes9Button).width(64).height(32);
            smallGun5Dialog.getButtonTable().add(no9Button).width(64).height(32);
            smallGun5Dialog.setObject(yes9Button, true);
            smallGun5Dialog.setObject(no9Button, false);

            mediumGun3Dialog = new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(2);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                }
            };
            mediumGun3Dialog.text("Upgrade Medium Gun 3 for 1000 moneyz?");
            mediumGun3Dialog.getButtonTable().add(yes10Button).width(64).height(32);
            mediumGun3Dialog.getButtonTable().add(no10Button).width(64).height(32);
            mediumGun3Dialog.setObject(yes10Button, true);
            mediumGun3Dialog.setObject(no10Button, false);

            mediumGun4Dialog = new Dialog("Medium Gun", Assets.getAssets().getSkin(), "dialog") {
                protected void result(Object object) {
                    if ((Boolean) object)
                        if (money_amount >= 1000) {
                            if ((Boolean) object)
                                world.ship.updateMediumGun(3);

                            if (!Game.MUTE)
                                shopClicked.play();
                            money_amount -= 1000;
                            money.setText(money_amount + "");
                        }
                }
            };
            mediumGun4Dialog.text("Upgrade Medium Gun 4 for 1000 moneyz?");
            mediumGun4Dialog.getButtonTable().add(yes11Button).width(64).height(32);
            mediumGun4Dialog.getButtonTable().add(no11Button).width(64).height(32);
            mediumGun4Dialog.setObject(yes11Button, true);
            mediumGun4Dialog.setObject(no11Button, false);
        }

        {//add dialogs to buttons
            smallGun1Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    smallGun1Dialog.show(stage);
                }
            });
            bigGun1Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    bigGun1Dialog.show(stage);
                }
            });
            dualGun1Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dualGun1Dialog.show(stage);
                }
            });
            mediumGun1Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mediumGun1Dialog.show(stage);
                }
            });
            smallGun2Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    smallGun2Dialog.show(stage);
                }
            });
            smallGun3Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    smallGun3Dialog.show(stage);
                }
            });
            mediumGun2Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mediumGun2Dialog.show(stage);
                }
            });
            smallGun4Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    smallGun4Dialog.show(stage);
                }
            });
            smallGun5Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    smallGun5Dialog.show(stage);
                }
            });
            mediumGun3Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mediumGun3Dialog.show(stage);
                }
            });
            mediumGun4Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mediumGun4Dialog.show(stage);
                }
            });
        }
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
        //( ͡° ͜ʖ ͡°) < l'elmar face
        //( ͡° ͜ʖ ͡°) < l'elmar face
        //( ͡° ͜ʖ ͡°) < l'elmar face
        //( ͡° ͜ʖ ͡°) < l'elmar face
        //( ͡° ͜ʖ ͡°) < l'elmar face
        //( ͡° ͜ʖ ͡°) < l'elmar face
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
        world.resize(width, height);
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