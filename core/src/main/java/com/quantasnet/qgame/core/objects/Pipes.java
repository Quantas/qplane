package com.quantasnet.qgame.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.quantasnet.qgame.core.QGame;

public class Pipes implements Poolable {

	private static final float WIDTH = 100f;
	private static final float HOLE = 200f;
	private static final float SPEED = 200f;
	
	private final Rectangle top = new Rectangle();
	private final Rectangle bottom = new Rectangle();
	private final Texture texture;
	
	public Pipes(final Texture texture) {
		this.texture = texture;
		reset();
	}
	
	public void move(final float deltaTime) {
		top.x -= deltaTime * SPEED;
		bottom.x -= deltaTime * SPEED;
	}
	
	public void render(final SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, top.x, top.y, top.width, top.height);
		spriteBatch.draw(texture, bottom.x, bottom.y, bottom.width, bottom.height);
	}
	
	public boolean isOffScreen() {
		return top.x + WIDTH < 0;
	}
	
	public boolean hitPlane(final Rectangle plane) {
		return top.overlaps(plane) || bottom.overlaps(plane);
	}
	
	@Override
	public void reset() {
		// calculate height
		final float height = (float) (Math.random() * 400) + 100;
		final float height2 = QGame.HEIGHT - HOLE - height;
		
		bottom.height = height;
		top.height = height2;
		
		top.width = WIDTH;
		top.y = QGame.HEIGHT - top.height;
		top.x = QGame.WIDTH + WIDTH;

		bottom.width = WIDTH;
		bottom.y = 0;
		bottom.x = QGame.WIDTH + WIDTH;
	}
}
