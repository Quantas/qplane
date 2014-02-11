package com.quantasnet.qgame.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.quantasnet.qgame.core.QGame;

public class QGameDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.title = "QGame";
		config.width = (int) QGame.WIDTH;
		config.height = (int) QGame.HEIGHT;
		new LwjglApplication(new QGame(), config);
	}
}
