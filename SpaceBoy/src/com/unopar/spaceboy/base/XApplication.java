package com.unopar.spaceboy.base;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import android.app.Application;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class XApplication extends Application {
	private static XApplication mInstance;
	
	private int mScreenWidth;
	private int mScreenHeight;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mInstance = this;
		
		Display display = ((WindowManager)getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		
		display.getMetrics(displayMetrics);
		
		mScreenHeight = displayMetrics.widthPixels;
		mScreenWidth = displayMetrics.heightPixels;
	}
	
	public static XApplication getInstance() {
		return mInstance;
	}
	
	public int getScreenWidth() {
		return mScreenWidth;
	}
	
	public int getScreenHeight() {
		return mScreenHeight;
	}
	
	public void configureAssetsFactory() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("font/");
	}
	
	public Engine createDefaultEngine() {
		Camera camera = new Camera(0, 0, mScreenWidth, mScreenHeight);
		RatioResolutionPolicy resolutionPolicy =
				new RatioResolutionPolicy(mScreenWidth, mScreenHeight);
		
		EngineOptions options = new EngineOptions(
				true, ScreenOrientation.LANDSCAPE_FIXED,
				resolutionPolicy, camera);
		
		return new Engine(options);
	}
}
