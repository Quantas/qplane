package com.quantasnet.qgame.core.objects;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Plane implements Serializable {

	private static final long serialVersionUID = 2840611519158311121L;

	private static final float GRAVITY = 600;

	private final Rectangle textureBox = new Rectangle();
	private final Rectangle hitBox = new Rectangle();
	private final Texture texture;

	private float velocity = 200;

	public Plane(final Texture texture) {
		this.texture = texture;
		textureBox.height = 64;
		textureBox.width = 64;
		textureBox.x = 100;
		textureBox.y = Gdx.graphics.getHeight() / 2 - textureBox.height / 2;

		hitBox.height = 15;
		hitBox.width = 55;
		hitBox.x = 105;
		hitBox.y = textureBox.y + (textureBox.height / 2) - 5;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public boolean hitBottom() {
		return hitBox.y < 0;
	}

	public void move(final float delta) {
		final float speed = velocity * delta;

		textureBox.y += speed;
		hitBox.y += speed;

		velocity -= GRAVITY * delta;

		if (Gdx.input.justTouched()) {
			velocity = 300;
		}
	}

	public void render(final SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, textureBox.x, textureBox.y);
	}
}
