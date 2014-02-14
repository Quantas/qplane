package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.quantasnet.qgame.core.QGame;

public class MainMenuScreen extends QScreenAdapter {
	private OrthographicCamera camera;
	private Texture background;
	private Sprite backgroundSprite;
	private float scrollTimer = 0.0f;

	public MainMenuScreen(final QGame game) {
		super(game);
		camera = new OrthographicCamera();
		
		background = new Texture(Gdx.files.internal("background2.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backgroundSprite = new Sprite(background, 0, 0, (int) QGame.WIDTH, (int) QGame.HEIGHT);
		backgroundSprite.setSize(QGame.WIDTH * 8, QGame.HEIGHT);
	}

	@Override
	public void render(float delta) {
		scrollTimer += delta;
		float time = scrollTimer / 16;
		if (time > 1.0f)
			scrollTimer = 0.0f;

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		//game.batch.setProjectionMatrix(camera.combined);

		backgroundSprite.setU(time);
		backgroundSprite.setU2(time + 1);

		game.font.setColor(Color.BLACK);
		
		game.batch.begin();
		backgroundSprite.draw(game.batch);
		game.font.draw(game.batch, "QGame", QGame.WIDTH / 2, QGame.HEIGHT / 2);
		game.font.draw(game.batch, "Touch to Start", QGame.WIDTH / 2, QGame.HEIGHT / 2 - 50);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
		game.fpsLog.log();
	}
	
	@Override
	public void dispose() {
		background.dispose();
	}
}