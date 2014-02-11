package com.quantasnet.qgame.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.quantasnet.qgame.core.QGame;

public class QGameDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.title = "QGame";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new QGame(), config);
	}
}
