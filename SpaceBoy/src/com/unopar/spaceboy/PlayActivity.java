package com.unopar.spaceboy;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.unopar.spaceboy.base.IXCollisionListener;
import com.unopar.spaceboy.base.XApplication;
import com.unopar.spaceboy.character.Enemy;
import com.unopar.spaceboy.character.ExplosionEffect;
import com.unopar.spaceboy.character.Sattelite;
import com.unopar.spaceboy.character.SpaceBoy;
import com.unopar.spaceboy.character.WeaponMissile;
import com.unopar.spaceboy.character.WeaponPool;
import com.unopar.spaceboy.character.WeaponPool.WeaponFactory;
import com.unopar.spaceboy.joystick.ControlListener;

public class PlayActivity extends BaseGameActivity {
	private BitmapTextureAtlas mBackgroundTextureAtlas;
	private BitmapTextureAtlas mParallaxTopTextureAtlas;
	private BitmapTextureAtlas mSpaceBoyTextureAtlas;

	private TextureRegion mBackgroundTextureRegion;
	private TextureRegion mParallaxTopTextureRegion;
	private TiledTextureRegion mSpaceBoyTextureRetion;

	private BitmapTextureAtlas mJoystickTextureAtlas;
	private TextureRegion mJoystickBaseTexture;
	private TextureRegion mJoystickKnobTexture;
	private TextureRegion mJoystickJumpButtonTexture;

	private BitmapTextureAtlas mEnemyTextureAtlas;
	private TiledTextureRegion mSatteliteTexture;
	
	private BitmapTextureAtlas mEffectTextureAtlas;
	private TiledTextureRegion mExplosionEffectTexture;
	
	private BitmapTextureAtlas mWeaponTextureAtlas;
	private TiledTextureRegion mWeaponMissileTexture;

	private Sound mImplosion;
	
	private HUD mGameHUD;

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

		mBackgroundTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 1024);
		mParallaxTopTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 1024);

		mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBackgroundTextureAtlas, getAssets(),
						"level1-background.png", 0, 0);
		mParallaxTopTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mParallaxTopTextureAtlas, getAssets(),
						"level1-parallax-top.png", 0, 0);

		mSpaceBoyTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 1024);
		mSpaceBoyTextureRetion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mSpaceBoyTextureAtlas, getAssets(),
						"character-spaceboy.png", 0, 0, 4, 2);

		mJoystickTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 1024);
		mJoystickBaseTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mJoystickTextureAtlas, getAssets(),
						"controle-base.png", 0, 0);
		mJoystickKnobTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mJoystickTextureAtlas, getAssets(),
						"controle-knob.png", 0, 512);
		mJoystickJumpButtonTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mJoystickTextureAtlas, getAssets(), 
						"controle-jump-button.png", 512, 0);

		mEnemyTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 1024,
				1024);
		mSatteliteTexture = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mEnemyTextureAtlas, getAssets(),
						"character-satellite.png", 0, 0, 1, 1);
		
		mEffectTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		mExplosionEffectTexture = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(
						mEffectTextureAtlas, getAssets(),
						"effect-explosions.png", 0, 0, 16, 8);
		
		mWeaponTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		mWeaponMissileTexture = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(
						mWeaponTextureAtlas, getAssets(),
						"weapon-missile.png", 0, 0, 17, 1);

		mBackgroundTextureAtlas.load();
		mParallaxTopTextureAtlas.load();
		mSpaceBoyTextureAtlas.load();
		mJoystickTextureAtlas.load();
		mEnemyTextureAtlas.load();
		mEffectTextureAtlas.load();	
		mWeaponTextureAtlas.load();		

		mImplosion = SoundFactory.createSoundFromAsset(
				getSoundManager(), this,
				"implosion.wav");

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

		int screenWidth = XApplication.getInstance().getScreenWidth();
		int screenHeight = XApplication.getInstance().getScreenHeight();

		Sprite spriteBackground = new Sprite(0, 0, screenWidth, screenHeight,
				mBackgroundTextureRegion, getVertexBufferObjectManager());
		Sprite spriteParallaxTop = new Sprite(0, 0, mParallaxTopTextureRegion,
				getVertexBufferObjectManager());

		AutoParallaxBackground parallax = new AutoParallaxBackground(0, 0, 0, 5);
		parallax.attachParallaxEntity(new ParallaxEntity(0, spriteBackground));
		parallax.attachParallaxEntity(new ParallaxEntity(-25, spriteParallaxTop));

		pScene.setBackground(parallax);

		final SpaceBoy boy = new SpaceBoy(20, 250, 
				mSpaceBoyTextureRetion, mEngine,
				new WeaponPool<WeaponMissile>(
						new WeaponFactory<WeaponMissile>() {
							@Override
							public WeaponMissile create() {
								return new WeaponMissile(
										mWeaponTextureAtlas, 
										getVertexBufferObjectManager(), 
										collisionListener);
							}
						}
				));
		pScene.attachChild(boy);
		
		final ExplosionEffect explosionEffect = new ExplosionEffect(
				mImplosion, mExplosionEffectTexture, mEngine);
		pScene.attachChild(explosionEffect);

		IXCollisionListener collisionListener = new IXCollisionListener() {
			@Override
			public void onCollision(AnimatedSprite obstacle) {
				if(obstacle instanceof Sattelite) {
					explosionEffect.show(
							obstacle.getX(), obstacle.getY());
				}
			}

			@Override
			public AnimatedSprite getMainCharacter() {
				return boy;
			}
		};

		Sattelite st1 = new Sattelite(mSatteliteTexture, mEngine,
				collisionListener);
		Sattelite st2 = new Sattelite(mSatteliteTexture, mEngine,
				collisionListener);
		Sattelite st3 = new Sattelite(mSatteliteTexture, mEngine,
				collisionListener);

		pScene.attachChild(st1);
		pScene.attachChild(st2);
		pScene.attachChild(st3);

		createHUD();

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	private void createHUD(final SpaceBoy boy, Scene pScene) {
		int screenHeight = XApplication.getInstance().getScreenHeight();
		int screenWidth = XApplication.getInstance().getScreenWidth();
		
		mGameHUD = new HUD();
		mGameHUD.setTouchAreaBindingOnActionDownEnabled(true);
		mGameHUD.setTouchAreaBindingOnActionMoveEnabled(true);
		
		final Sprite jumpButton = new Sprite(
				screenWidth - mJoystickJumpButtonTexture.getWidth() - 50,
				screenWidth - mJoystickJumpButtonTexture.getHeight() - 30,
				mJoystickJumpButtonTexture, getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					boy.shoot();
				}
				
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
				
		AnalogOnScreenControl control = new AnalogOnScreenControl(
				20,
				screenHeight - mJoystickBaseTexture.getHeight() - 20, 
				mEngine.getCamera(), mJoystickBaseTexture,
				mJoystickKnobTexture, 0.1f, getVertexBufferObjectManager(),
				new ControlListener(boy));

		control.refreshControlKnobPosition();
		control.setAlpha(0.5f);
		
		mGameHUD.attachChild(jumpButton);
		mGameHUD.registerTouchArea(jumpButton);
		
		mGameHUD.setChildScene(control);
		
		mEngine.getCamera().setHUD(mGameHUD);
		
	}

}
