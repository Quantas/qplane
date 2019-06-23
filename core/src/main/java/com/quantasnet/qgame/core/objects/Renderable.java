package com.quantasnet.qgame.core.objects;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable extends Serializable {
	void move(float deltaTime);
	void render(SpriteBatch batch);
}
