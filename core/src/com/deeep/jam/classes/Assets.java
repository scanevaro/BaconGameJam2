package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {

    private Texture shopButton;
    private Music mainMenuMusic;
    private Music inGameMusic1;
    private Music inGameMusic2;
    private Sound shoot;
    private Sound shopClick;
    private Sound selected;

    /**
     * instance for singleton
     */
    private static Assets assets;
    /**
     * Just a check to be sure that the assets aren't loaded multiple times
     */
    private static boolean loaded = false;
    /**
     * The atlases containing all the images
     */
    private TextureAtlas textureAtlas;

    private BitmapFont font;
    /**
     * Skin for menus and buttons
     */
    private Skin skin;

    /**
     * Logo for SplashScreen
     */
    private Texture logo;

    /**
     * Game Title
     */
    private Texture title;


    /**
     * Logger instance
     */
//    private Logger logger = Logger.getInstance();

    /**
     * Simple singleton
     *
     * @return assets instance
     */
    public static Assets getAssets() {
        if (assets == null) {
            assets = new Assets();
        }
        return assets;
    }

    /**
     * function to load everything. Nothing special. TODO add more resources here( sound music etc)
     */
    public void load() {
        if (!loaded) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("TextureAtlass.txt"));
            loadFont();
            loadSkin();
            logo = new Texture(Gdx.files.internal("data/newLogo.png"));
            title = new Texture(Gdx.files.internal("data/title.png"));
//            logger.system(Assets.class, "All assets have been loaded");
            shopButton = new Texture(Gdx.files.internal("data/button-shop.png"));
            mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/Take_a_Chance.mp3"));
            inGameMusic1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/BlackVortex.mp3"));
            inGameMusic2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/TheComplex.mp3"));
            shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
            selected = Gdx.audio.newSound(Gdx.files.internal("sounds/selected.mp3"));
            shopClick = Gdx.audio.newSound(Gdx.files.internal("sounds/shopClick.mp3"));
            loaded = true;
        }
    }

    private void loadFont() {
        // Instead of using the default font that comes with libgdx default skin, we use a TrueType font and load it in runtime.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Toucon.ttf"));
        font = generator.generateFont(12);
    }

    private void loadSkin() {
        skin = new Skin();
        skin.add("default-font", font, BitmapFont.class);
        FileHandle fileHandle = Gdx.files.internal("data/uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    /**
     * Returns an texture region based on the name given. Get images using this function!
     *
     * @param name see TextureAtlas.txt
     * @return the texture region connected to the name
     */
    public TextureRegion getRegion(String name) {
        TextureRegion textureRegion = textureAtlas.findRegion(name);
        if (textureRegion == null) {
//            logger.error(Assets.class, "Unkown texture requested: " + name);
        }
        return textureAtlas.findRegion(name);
    }

    public TextureRegion getRegion(String name, int index) {
        TextureRegion textureRegion = textureAtlas.findRegion(name, index);
        if (textureRegion == null) {
//            logger.error(Assets.class, "Unkown texture requested: " + name);
        }
        return textureAtlas.findRegion(name, index);
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getLogo() {
        return logo;
    }

    public Texture getTitle() {
        return title;
    }

    public void dispose() {
        textureAtlas.dispose();
        skin.dispose();
        logo.dispose();
        title.dispose();
        mainMenuMusic.dispose();
        inGameMusic1.dispose();
        inGameMusic2.dispose();
        shoot.dispose();
        shopClick.dispose();
        selected.dispose();
        shopButton.dispose();
    }

    public Music getMainMenuMusic() {
        return mainMenuMusic;
    }

    public Music getInGameMusic1() {
        return inGameMusic1;
    }

    public Music getInGameMusic2() {
        return inGameMusic2;
    }

    public Sound getShootSound() {
        return shoot;
    }

    public Sound getShopClick() {
        return shopClick;
    }

    public Sound getSelected() {
        return selected;
    }

    public Texture getShopButton() {
        return shopButton;
    }
}