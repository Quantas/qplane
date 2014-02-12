package com.quantasnet.qgame.core.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.quantasnet.qgame.core.QGame;

public abstract class QScreenAdapter extends ScreenAdapter {
	
	protected final QGame game;
	
	public QScreenAdapter(final QGame game) {
		this.game = game;
	}
}
