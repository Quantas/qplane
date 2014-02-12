package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.quantasnet.qgame.core.QGame;

public class GameScreen extends QScreenAdapter {
	
	private final Texture planeImage;
	private Texture background;
	private Sprite backgroundSprite;
	private final OrthographicCamera camera;
	private final Rectangle plane;
	
	private float scrollTimer = 0.0f;

	public GameScreen(final QGame game) {
		super(game);

		background = new Texture(Gdx.files.internal("background.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backgroundSprite = new Sprite(background, 0, 0, (int) QGame.WIDTH, (int) QGame.HEIGHT);
		backgroundSprite.setSize(QGame.WIDTH * 3, QGame.HEIGHT);
		
		planeImage = new Texture(Gdx.files.internal("plane.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, QGame.WIDTH, QGame.HEIGHT);

		plane = new Rectangle();
		plane.height = 56;
		plane.width = 56;
		plane.x = 100;
		plane.y = QGame.HEIGHT / 2 - plane.height / 2;
	}

	@Override
	public void render(float delta) {
		scrollTimer += delta;
		float time = scrollTimer / 8;
		if (time > 1.0f)
			scrollTimer = 0.0f;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		backgroundSprite.setU(time);
		backgroundSprite.setU2(time + 1);
		
		if (Gdx.input.isTouched()) {
			plane.y += delta * 500;
		} else {		
			plane.y -= (delta * 400);
		}
		
		game.batch.begin();
		backgroundSprite.draw(game.batch);
		game.batch.draw(planeImage, plane.x, plane.y);
		game.batch.end();

		game.fpsLog.log();
	}

	@Override
	public void dispose() {
		planeImage.dispose();
	}
}
