package com.quantasnet.qgame.core.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.quantasnet.qgame.core.QGame;
import com.quantasnet.qgame.core.objects.Pipes;
import com.quantasnet.qgame.core.objects.Plane;

public class GameScreen extends QScreenAdapter {
	
	private static final long PIPE_TIME = 2000000000L;
	
	private final Texture planeImage;
	private final Texture pipeImage;
	private final List<Pipes> currentPipes = new ArrayList<Pipes>();
	private final Pool<Pipes> pipes;
	private Texture background;
	private Sprite backgroundSprite;
	private final Plane plane;
	private final BitmapFont font;
	
	private float scrollTimer = 0.0f;
	private long lastPipeTime = TimeUtils.nanoTime();
	private int score = 0;
	
	public GameScreen(final QGame game) {
		super(game);
		
		font = new BitmapFont();
		
		background = new Texture(Gdx.files.internal("background2.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backgroundSprite = new Sprite(background, 0, 0, (int)QGame.WIDTH, (int)QGame.HEIGHT);
		backgroundSprite.setSize(Gdx.graphics.getWidth() * 8, Gdx.graphics.getHeight());
		
		planeImage = new Texture(Gdx.files.internal("plane.png"));
		pipeImage = new Texture(Gdx.files.internal("pipe.png"));
		
		pipes = new Pool<Pipes>(6, 10) {
			@Override
			protected Pipes newObject() {
				return new Pipes(pipeImage);
			}
		};
		
		currentPipes.add(pipes.obtain());
		
		plane = new Plane(planeImage);
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
		
		plane.move(delta);
		
		game.batch.begin();
		backgroundSprite.draw(game.batch);
		plane.render(game.batch);
		for (final Pipes  pipes : currentPipes) {
			pipes.render(game.batch);
		}
		font.setColor(Color.BLACK);
		font.setScale(3f, 3f);
		font.draw(game.batch, Integer.toString(score), (Gdx.graphics.getWidth() / 2) - 20, Gdx.graphics.getHeight() - 20);
		game.batch.end();

		if (TimeUtils.nanoTime() - lastPipeTime > PIPE_TIME) {
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
			if (pipe.hitPlane(plane) || plane.hitBottom()) {
				// game over
				game.setScreen(new MainMenuScreen(game));
			}
			if (pipe.scored(plane)) {
				score++;
			}
		}
	}

	@Override
	public void dispose() {
		planeImage.dispose();
		pipeImage.dispose();
		background.dispose();
		pipes.clear();
	}
}
