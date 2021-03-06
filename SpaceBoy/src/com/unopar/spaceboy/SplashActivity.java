package com.unopar.spaceboy;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;

import com.unopar.spaceboy.base.XApplication;

public class SplashActivity extends BaseGameActivity {
	private ITextureRegion mTextureRegion;
	private BitmapTextureAtlas mTextureAtlas;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final int screenWidth = XApplication.getInstance().getScreenWidth();
		final int screenHeight = XApplication.getInstance().getScreenHeight();

		Camera camera = new Camera(0, 0, screenWidth, screenHeight);

		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						screenWidth, screenHeight), camera);

		new Engine(options);

		return options;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		XApplication.getInstance().configureAssetsFactory();

		mTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 128, 128);
		mTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mTextureAtlas, getAssets(), "splash.png", 0, 0);

		mTextureAtlas.load();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		Scene scene = new Scene();

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		Sprite icon = new Sprite(0, 0, mTextureRegion,
				getVertexBufferObjectManager());

		Camera camera = mEngine.getCamera();
		icon.setPosition((camera.getWidth() - icon.getWidth()) / 2,
				(camera.getHeight() - icon.getHeight()) / 2);

		pScene.attachChild(icon);

		mEngine.registerUpdateHandler(new TimerHandler(3, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						startActivity(
								new Intent(
										SplashActivity.this,
										MenuActivity.class));

						finish();
					}
				});
			}
		}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
