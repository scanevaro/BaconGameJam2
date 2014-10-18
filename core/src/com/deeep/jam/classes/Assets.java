package com.deeep.jam.classes;

import com.badlogic.gdx.Gdx;
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
        TextureRegion textureRegion = textureAtlas.findRegion(name,index);
        if (textureRegion == null) {
//            logger.error(Assets.class, "Unkown texture requested: " + name);
        }
        return textureAtlas.findRegion(name);
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
    }

    private Texture shopButton;

    public Texture getShopButton() {
        return shopButton;
    }
}