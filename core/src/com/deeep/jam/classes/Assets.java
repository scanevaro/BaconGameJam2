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
import com.badlogic.gdx.utils.Array;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {

    private Texture canonIndexReferenceTexture;
    private Texture gameOverTexture;
    private Texture aboutTexture;
    private Music mainMenuMusic;
    private Music inGameMusic;
    private Sound big1;
    private Sound big2;
    private Sound small;
    private Sound medium;
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
//            textureAtlas = new TextureAtlas(Gdx.files.internal("TextureAtlass.txt"));
            loadFont();
            loadSkin();
            loadTextures();
            loadMusic();
            loadSFX();
            loadAtlas();

            loaded = true;
        }
    }

    private void loadSkin() {
        skin = new Skin();
        //add font
        skin.add("default-font", font, BitmapFont.class);

        FileHandle fileHandle = Gdx.files.internal("data/skin.json");
        FileHandle atlasFile = fileHandle.sibling("skin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    private void loadFont() {
        // Instead of using the default font that comes with libgdx default skin, we use a TrueType font and load it in runtime.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Toucon.ttf"));
        font = generator.generateFont(12);
    }

    private void loadTextures() {
        logo = new Texture(Gdx.files.internal("data/newLogo.png"));
        title = new Texture(Gdx.files.internal("data/title.png"));
        canonIndexReferenceTexture = new Texture(Gdx.files.internal("canonIndexReference2.png"));
        gameOverTexture = new Texture(Gdx.files.internal("data/gameOver.png"));
        aboutTexture = new Texture(Gdx.files.internal("data/about.png"));
    }

    private void loadMusic() {
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/Take_a_Chance.mp3"));
        inGameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/VolatileReaction.mp3"));
    }

    private void loadSFX() {
        big1 = Gdx.audio.newSound(Gdx.files.internal("sounds/big1.mp3"));
        big2 = Gdx.audio.newSound(Gdx.files.internal("sounds/big2.mp3"));
        medium = Gdx.audio.newSound(Gdx.files.internal("sounds/medium.mp3"));
        small = Gdx.audio.newSound(Gdx.files.internal("sounds/small.mp3"));
        selected = Gdx.audio.newSound(Gdx.files.internal("sounds/selected.mp3"));
        shopClick = Gdx.audio.newSound(Gdx.files.internal("sounds/shopClick.mp3"));
    }

    private void loadAtlas() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("TextureAtlass.txt"));
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("data/assetsPack.pack"));
        skin.addRegions(buttonAtlas);
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

    public Array<TextureAtlas.AtlasRegion> getTextureRegions(String name) {
        return textureAtlas.findRegions(name);
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
        inGameMusic.dispose();
        big1.dispose();
        big2.dispose();
        medium.dispose();
        small.dispose();
        shopClick.dispose();
        selected.dispose();
        canonIndexReferenceTexture.dispose();

        loaded = false;
    }

    public Music getMainMenuMusic() {
        return mainMenuMusic;
    }

    public Music getInGameMusic() {
        return inGameMusic;
    }

    public Sound getBig1() {
        return big1;
    }

    public Sound getShopClick() {
        return shopClick;
    }

    public Sound getSelected() {
        return selected;
    }

    public Sound getBig2() {
        return big2;
    }

    public Sound getSmall() {
        return small;
    }

    public Sound getMedium() {
        return medium;
    }

    public Texture getCanonIndexReferenceTexture() {
        return canonIndexReferenceTexture;
    }

    public Texture getGameOverTexture() {
        return gameOverTexture;
    }

    public Texture getAboutTexture() {
        return aboutTexture;
    }
}