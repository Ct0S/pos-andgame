package com.unopar.unomario;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Bitmap;

import com.unopar.unomario.base.XApplication;

public class PlayActivity extends BaseGameActivity {
	private TMXTiledMap mTMXTiledMap;
	
	private HUD mHUD;

	private TextureRegion mJumpButtonTexture;
	private TextureRegion mLeftButtonTexture;
	private TextureRegion mRightButtonTexture;	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Engine engine = XApplication.getInstance().createDefaultEngine();
		
		return engine.getEngineOptions();
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		XApplication.getInstance().configureAssetsFactory();
		
		TMXLoader tmxLoader = new TMXLoader(getAssets(), 
				mEngine.getTextureManager(), mEngine.getVertexBufferObjectManager());
		mTMXTiledMap = tmxLoader.loadFromAsset("world1.tmx");
		
		BitmapTextureAtlas mJoystickTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 1024);
		mJumpButtonTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mJoystickTextureAtlas, getAssets(), 
						"game-jump-button.png", 0, 0);
		mLeftButtonTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mJoystickTextureAtlas, getAssets(),
						"game-left-button.png", 0, 256);
		mRightButtonTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mJoystickTextureAtlas, getAssets(),
						"game-right-button.png", 0, 512);
		
		mJoystickTextureAtlas.load();
				
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		pOnCreateSceneCallback.onCreateSceneFinished(new Scene());
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		createObjects(pScene);
		createHUD();		
		
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private void createHUD() {
		mHUD = new HUD();
		mHUD.setTouchAreaBindingOnActionDownEnabled(true);
		mHUD.setTouchAreaBindingOnActionMoveEnabled(true);
		
		int screenWidth = XApplication.getInstance().getScreenWidth();
		int screenHeight = XApplication.getInstance().getScreenHeight();
		
		final Sprite spriteJumpButton = new Sprite(
				screenWidth - 50 - mJumpButtonTexture.getWidth(),
				screenHeight - 30 - mJumpButtonTexture.getHeight(),
				mJumpButtonTexture, getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					// TODO: Jump Mario
				}
				return true;
			}
		};
		
		final Sprite spriteLeftButton = new Sprite(
				20,
				screenHeight - 30 - mLeftButtonTexture.getHeight(),
				mLeftButtonTexture, getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					// TODO: Move Left Mario
				}
				return true;
			}
		};
		
		final Sprite spriteRightButton = new Sprite(
				90,
				screenHeight - 30 - mRightButtonTexture.getHeight(),
				mRightButtonTexture, getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					// TODO: Move Right Mario
				}
				return true;
			}
		};
		
		mHUD.attachChild(spriteJumpButton);
		mHUD.attachChild(spriteLeftButton);
		mHUD.attachChild(spriteRightButton);
		
		mHUD.registerTouchArea(spriteJumpButton);
		mHUD.registerTouchArea(spriteLeftButton);
		mHUD.registerTouchArea(spriteRightButton);
		
		mEngine.getCamera().setHUD(mHUD);
	}

	private void createObjects(Scene pScene) {
		for (int i = 0; i < mTMXTiledMap.getTMXLayers().size(); i++) {
		    TMXLayer layer = mTMXTiledMap.getTMXLayers().get(i);
		    pScene.attachChild(layer);
		}
	}
}
