package com.quantasnet.qgame.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Pipes implements Renderable, Poolable {
	private static final long serialVersionUID = -1589967695469956145L;
	
	private static final float WIDTH = 100f;
	private static final float HOLE = 200f;
	private static final float SPEED = 200f;
	
	private final Rectangle top = new Rectangle();
	private final Rectangle bottom = new Rectangle();
	private final Texture texture;
	private boolean scored = false;
	
	public Pipes(final Texture texture) {
		this.texture = texture;
		reset();
	}
	
	@Override
	public void move(final float deltaTime) {
		top.x -= deltaTime * SPEED;
		bottom.x -= deltaTime * SPEED;
	}
	
	@Override
	public void render(final SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, top.x, top.y, top.width, top.height);
		spriteBatch.draw(texture, bottom.x, bottom.y, bottom.width, bottom.height);
	}
	
	public boolean isOffScreen() {
		return top.x + WIDTH < 0;
	}
	
	public boolean hitPlane(final Plane plane) {
		return top.overlaps(plane.getHitBox()) || bottom.overlaps(plane.getHitBox());
	}
	
	public boolean scored(final Plane plane) {
		if (!scored) {
			if (top.x < plane.getHitBox().x) {
				scored = true;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void reset() {
		// calculate height
		final float height = (float) (Math.random() * (Gdx.graphics.getHeight() - 600)) + 100;
		final float height2 = Gdx.graphics.getHeight() - HOLE - height;
		
		top.height = height;
		bottom.height = height2;
		
		top.width = WIDTH;
		top.y = Gdx.graphics.getHeight() - top.height;
		top.x = Gdx.graphics.getWidth() + WIDTH;

		bottom.width = WIDTH;
		bottom.y = 0;
		bottom.x = Gdx.graphics.getWidth() + WIDTH;
		
		scored = false;
	}
}
