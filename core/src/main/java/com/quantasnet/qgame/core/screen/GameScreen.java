package com.quantasnet.qgame.core.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.quantasnet.qgame.core.QGame;
import com.quantasnet.qgame.core.objects.Pipes;

public class GameScreen extends QScreenAdapter {
	
	private final Texture planeImage;
	private final Texture pipeImage;
	private final List<Pipes> currentPipes = new ArrayList<Pipes>();
	private final Pool<Pipes> pipes;
	private Texture background;
	private Sprite backgroundSprite;
	private final OrthographicCamera camera;
	private final Rectangle plane;
	
	private float velocity = 200;
	
	private float scrollTimer = 0.0f;
	
	private final long pipeTimer = 2000000000L;
	
	private long lastPipeTime = TimeUtils.nanoTime();

	public GameScreen(final QGame game) {
		super(game);

		background = new Texture(Gdx.files.internal("background2.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backgroundSprite = new Sprite(background, 0, 0, (int)QGame.WIDTH, (int)QGame.HEIGHT);
		backgroundSprite.setSize(QGame.WIDTH * 8, QGame.HEIGHT);
		
		planeImage = new Texture(Gdx.files.internal("plane.png"));
		pipeImage = new Texture(Gdx.files.internal("pipe.png"));
		
		pipes = new Pool<Pipes>(6, 20) {
			@Override
			protected Pipes newObject() {
				return new Pipes(pipeImage);
			}
		};
		
		currentPipes.add(pipes.obtain());
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, QGame.WIDTH, QGame.HEIGHT);

		plane = new Rectangle();
		plane.height = 64;
		plane.width = 64;
		plane.x = 100;
		plane.y = QGame.HEIGHT / 2 - plane.height / 2;
	}

	@Override
	public void render(float delta) {
		scrollTimer += delta;
		float time = scrollTimer / 16;
		if (time > 1.0f)
			scrollTimer = 0.0f;
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		backgroundSprite.setU(time);
		backgroundSprite.setU2(time + 1);
		
		plane.y += velocity * delta;
		velocity -= 250 * delta;
		if (Gdx.input.justTouched()) {
			velocity = 200;
		}
		
		game.batch.begin();
		backgroundSprite.draw(game.batch);
		game.batch.draw(planeImage, plane.x, plane.y);
		for (final Pipes  pipes : currentPipes) {
			pipes.render(game.batch);
		}
		game.batch.end();

		if (TimeUtils.nanoTime() - lastPipeTime > pipeTimer) {
			currentPipes.add(pipes.obtain());
			lastPipeTime = TimeUtils.nanoTime();
		}
		
		final Iterator<Pipes> pipesIterator = currentPipes.iterator();
		while(pipesIterator.hasNext()) {
			final Pipes pipe = pipesIterator.next();
			pipe.move(delta);
			if (pipe.isOffScreen()) {
				pipesIterator.remove();
				pipes.free(pipe);
			}
			if (pipe.hitPlane(plane)) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
		game.fpsLog.log();
	}

	@Override
	public void dispose() {
		planeImage.dispose();
		pipeImage.dispose();
		background.dispose();
		pipes.clear();
	}
}
