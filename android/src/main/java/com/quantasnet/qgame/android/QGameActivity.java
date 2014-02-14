package com.quantasnet.qgame.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.quantasnet.qgame.core.QGame;

public class QGameActivity extends AndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.useGL20 = true;
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new QGame(), config);
	}
}
