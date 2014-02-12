package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.quantasnet.qgame.core.QGame;

public class SplashScreen extends QScreenAdapter {

	private SpriteBatch spriteBatch;
	private Texture splash;
	private OrthographicCamera camera;
	private long startTime;

	public SplashScreen(final QGame game) {
		super(game);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, QGame.WIDTH, QGame.HEIGHT);
		spriteBatch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(splash, 0, 0);
		spriteBatch.end();
		if (TimeUtils.millis() > (startTime + 2000)) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void show() {
		splash = new Texture(Gdx.files.internal("splash.png"));
		startTime = TimeUtils.millis();
	}
	
	@Override
	public void dispose() {
		splash.dispose();
		spriteBatch.dispose();
	}
}
