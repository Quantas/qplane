package com.quantasnet.qgame.core.objects;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.quantasnet.qgame.core.QGame;

public class Plane implements Serializable {

	private static final long serialVersionUID = 2840611519158311121L;

	private static final float GRAVITY = 600;

	private final Rectangle textureBox = new Rectangle();
	private final Rectangle hitBox = new Rectangle();
	private final ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final Texture texture;

	private float velocity = 200;

	public Plane(final Texture texture) {
		this.texture = texture;
		textureBox.height = 64;
		textureBox.width = 64;
		textureBox.x = 100;
		textureBox.y = QGame.HEIGHT / 2 - textureBox.height / 2;

		hitBox.height = 15;
		hitBox.width = 55;
		hitBox.x = 105;
		hitBox.y = textureBox.y + (textureBox.height / 2) - 5;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void move(final float delta) {
		final float speed = velocity * delta;

		textureBox.y += speed;
		hitBox.y += speed;

		velocity -= GRAVITY * delta;

		if (Gdx.input.justTouched()) {
			velocity = 200;
		}
	}

	public void render(final SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, textureBox.x, textureBox.y);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		//shapeRenderer.setColor(0, 0, 1, 1);
		//shapeRenderer.rect(textureBox.x, textureBox.y, textureBox.width, textureBox.height);
		shapeRenderer.end();
	}
}
