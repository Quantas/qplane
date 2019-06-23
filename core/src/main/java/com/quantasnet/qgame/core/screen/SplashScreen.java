package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.TimeUtils;
import com.quantasnet.qgame.core.QGame;

public class SplashScreen extends QScreenAdapter {

	private long startTime;

	public SplashScreen(final QGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.font.setScale(2f);
		game.font.draw(game.batch, "Quanasnet", (Gdx.graphics.getWidth() / 2) - 70, Gdx.graphics.getHeight() / 2);
		game.batch.end();
		if (TimeUtils.millis() > (startTime + 1500)) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void show() {
		startTime = TimeUtils.millis();
	}
}
