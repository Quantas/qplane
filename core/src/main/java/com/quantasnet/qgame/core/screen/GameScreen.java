package com.quantasnet.qgame.core.screen;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.quantasnet.qgame.core.QGame;
import com.quantasnet.qgame.core.objects.Raindrop;

public class GameScreen extends QScreenAdapter {
	private final Texture dropImage;
	private final Texture bucketImage;
	private Texture background;
	private Sprite backgroundSprite;
	private final Sound dropSound;
	private final Music rainMusic;
	private final OrthographicCamera camera;
	private final Rectangle bucket;
	private final Array<Raindrop> raindrops;
	private final Pool<Raindrop> dropPool;
	
	private long lastDropTime;
	private int dropsGathered;
	private float scrollTimer = 0.0f;

	public GameScreen(final QGame game) {
		super(game);

		background = new Texture(Gdx.files.internal("background.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backgroundSprite = new Sprite(background, 0, 0, (int) QGame.WIDTH, (int) QGame.HEIGHT);
		backgroundSprite.setSize(QGame.WIDTH * 3, QGame.HEIGHT);
		
		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, QGame.WIDTH, QGame.HEIGHT);

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = QGame.WIDTH / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Raindrop>();
		dropPool = new Pool<Raindrop>(10, 15) {
			@Override
			protected Raindrop newObject() {
				return new Raindrop();
			}
		};
		
		spawnRaindrop();
	}

	private void spawnRaindrop() {
		raindrops.add(dropPool.obtain());
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		scrollTimer += delta;
		float time = scrollTimer / 8;
		if (time > 1.0f)
			scrollTimer = 0.0f;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		backgroundSprite.setU(time);
		backgroundSprite.setU2(time + 1);
		
		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		backgroundSprite.draw(game.batch);
		game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, QGame.HEIGHT);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (final Raindrop raindrop : raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			final Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		}

		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0) {
			bucket.x = 0;
		}
		if (bucket.x > QGame.WIDTH - 64) {
			bucket.x = QGame.WIDTH - 64;
		}

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			spawnRaindrop();
		}

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we increase the
		// value our drops counter and add a sound effect.
		final Iterator<Raindrop> iter = raindrops.iterator();
		while (iter.hasNext()) {
			final Raindrop raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				iter.remove();
				dropPool.free(raindrop);
			}
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				dropSound.play();
				iter.remove();
				dropPool.free(raindrop);
			}
		}
		
		game.fpsLog.log();
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		dropPool.clear();
	}
}
