package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.quantasnet.qgame.core.QGame;

public abstract class QScreenAdapter extends ScreenAdapter {

	protected final QGame game;

	public QScreenAdapter(final QGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		// update camera
		game.camera.update();
		
		// clear previous frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
}
