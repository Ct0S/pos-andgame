package com.unopar.unomario;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;

import com.unopar.unomario.base.XApplication;

public class MenuActivity extends BaseGameActivity implements
		IOnMenuItemClickListener {
	private final int MENU_PLAY = 0x01;
	private final int MENU_QUIT = 0x09;

	private Font mFont;
	private BitmapTextureAtlas mTextureAtlas;

	private BitmapTextureAtlas mBackgroundTextureAtlas;
	private TextureRegion mBackgroundTextureRegion;

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

		mTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mFont = FontFactory
				.createFromAsset(getFontManager(), mTextureAtlas, getAssets(),
						"font.ttf", 36, true, android.graphics.Color.WHITE);

		mBackgroundTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1920, 1200);
		mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBackgroundTextureAtlas, getAssets(),
						"menu-background.jpg", 0, 0);

		mTextureAtlas.load();
		mFont.load();
		mBackgroundTextureAtlas.load();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		MenuScene scene = new MenuScene(mEngine.getCamera(), this);

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

		int screenWidht = XApplication.getInstance().getScreenWidth();
		int screenHeight = XApplication.getInstance().getScreenHeight();

		Sprite backgroundSprint = new Sprite(0, 0, screenWidht, screenHeight,
				mBackgroundTextureRegion,
				mEngine.getVertexBufferObjectManager());
		pScene.attachChild(backgroundSprint);

		IMenuItem menuPlay = new ScaleMenuItemDecorator(new TextMenuItem(
				MENU_PLAY, mFont, "Iniciar Jogo",
				getVertexBufferObjectManager()), 1.2f, 1);
		
		IMenuItem menuQuit = new ScaleMenuItemDecorator(new TextMenuItem(
				MENU_QUIT, mFont, "Sair", getVertexBufferObjectManager()),
				0.8f, 1.0f);
		
		MenuScene menu = (MenuScene) pScene;
		menu.addMenuItem(menuPlay);
		menu.addMenuItem(menuQuit);

		menu.buildAnimations();

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_PLAY:
			startActivity(new Intent(this, PlayActivity.class));
			break;

		case MENU_QUIT:
			finish();
			break;

		default:
			break;
		}

		return false;
	}

}
