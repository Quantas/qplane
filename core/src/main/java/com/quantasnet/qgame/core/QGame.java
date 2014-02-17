package com.quantasnet.qgame.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quantasnet.qgame.core.screen.SplashScreen;

public class QGame extends Game {
	public static final float WIDTH = 480;
	public static final float HEIGHT = 800;
	
	public SpriteBatch batch;
    public BitmapFont font;
    public final FPSLogger fpsLog = new FPSLogger();

    public void create() {
    	Texture.setEnforcePotImages(false);
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new SplashScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
