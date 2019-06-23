package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.quantasnet.qgame.core.QGame;

public class MainMenuScreen extends QScreenAdapter {
	private Texture background;
	private Sprite backgroundSprite;
	private float scrollTimer = 0.0f;

	public MainMenuScreen(final QGame game) {
		super(game);
		
		background = new Texture(Gdx.files.internal("background2.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backgroundSprite = new Sprite(background, 0, 0, (int) QGame.WIDTH, (int) QGame.HEIGHT);
		backgroundSprite.setSize(Gdx.graphics.getWidth() * 8, Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		scrollTimer += delta;
		float time = scrollTimer / 16;
		if (time > 1.0f)
			scrollTimer = 0.0f;

		backgroundSprite.setU(time);
		backgroundSprite.setU2(time + 1);

		game.font.setColor(Color.BLACK);
		
		game.batch.begin();
		backgroundSprite.draw(game.batch);
		game.font.setScale(1f);
		game.font.draw(game.batch, "QGame", (Gdx.graphics.getWidth() / 2) - 70, Gdx.graphics.getHeight() / 2);
		game.font.draw(game.batch, "Touch to Start", (Gdx.graphics.getWidth() / 2) - 70, Gdx.graphics.getHeight() / 2 - 50);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}
	
	@Override
	public void dispose() {
		background.dispose();
	}
}