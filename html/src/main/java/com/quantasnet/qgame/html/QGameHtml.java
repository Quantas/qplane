package com.quantasnet.qgame.html;

import com.quantasnet.qgame.core.QGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class QGameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new QGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
