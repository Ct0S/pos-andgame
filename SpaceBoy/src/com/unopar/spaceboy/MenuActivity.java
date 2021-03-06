package com.unopar.spaceboy;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.content.Intent;

import com.unopar.spaceboy.base.XApplication;

public class MenuActivity extends BaseGameActivity implements
		IOnMenuItemClickListener {
	private final int MENU_PLAY = 0x01;
	private final int MENU_QUIT = 0x09;
	
	private Font mFont;
	private BitmapTextureAtlas mTextureAtlas;

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

		mFont = FontFactory.createFromAsset(getFontManager(), mTextureAtlas,
				getAssets(), "KOMIKAX_.ttf", 36, true,
				android.graphics.Color.WHITE);

		mTextureAtlas.load();
		getFontManager().loadFont(mFont);

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		MenuScene scene = new MenuScene(mEngine.getCamera(), this);
		scene.setBackground(new Background(Color.GREEN));

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		
		IMenuItem menuPlay = new ColorMenuItemDecorator(
				new TextMenuItem(
						MENU_PLAY, mFont, "Iniciar Jogo",
						getVertexBufferObjectManager()), 
				Color.BLUE, Color.WHITE);
		menuPlay.setBlendFunction(
				GL10.GL_SRC_ALPHA, 
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		IMenuItem menuQuit = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_QUIT, mFont, "Sair", getVertexBufferObjectManager()),
				Color.BLUE, Color.RED);		
		menuQuit.setBlendFunction(
				GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		MenuScene menu = (MenuScene)pScene;
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
