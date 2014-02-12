package com.quantasnet.qgame.core.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.quantasnet.qgame.core.QGame;

public class Raindrop extends Rectangle implements Poolable{
	private static final long serialVersionUID = 3502664640865404887L;

	public Raindrop() {
		reset();
	}
	
	@Override
	public void reset() {
		x = MathUtils.random(0, QGame.WIDTH - 64);
		y = QGame.HEIGHT;
		width = 64;
		height = 64;
	}
}
