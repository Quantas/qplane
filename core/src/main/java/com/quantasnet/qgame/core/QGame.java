package com.quantasnet.qgame.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.quantasnet.qgame.core.screen.SplashScreen;

public class QGame extends Game {
	public static final float WIDTH = 512;
	public static final float HEIGHT = 1024;
	public static final float RATIO = WIDTH / HEIGHT;

	public SpriteBatch batch;
	public BitmapFont font;
	public Rectangle viewport;
	public OrthographicCamera camera;
	
	public void create() {
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
