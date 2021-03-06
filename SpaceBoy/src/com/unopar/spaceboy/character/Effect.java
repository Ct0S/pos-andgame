package com.unopar.spaceboy.character;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.view.animation.Animation;

public abstract class Effect extends AnimatedSprite {
	private Sound mSound;
	private boolean mInAnimation;
	private IAnimationListener mAnimationListener;
	
	public Effect(Sound sound, 
			ITiledTextureRegion textureRegion, Engine engine) {
		super(0, 0, textureRegion, engine.getVertexBufferObjectManager());
		
		mSound = sound;
		setVisible(false);
		
		mAnimationListener = new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				mInAnimation = true;
				mSound.play();
			}
			
			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
			}
			
			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
			}
			
			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				mInAnimation = false;
				setVisible(false);
			}
		};
	}
	
	public void show(float x, float y) {
		if(mInAnimation) {
			stopAnimation();
		}
		
		setPosition(x, y);
		setVisible(true);
		
		animate();		
	}
	
	private void animate() {
		int firstFrameIndex = getFirstFrameIndex();
		int lastFrameIndex = getLastFrameIndex();
		
		long[] framesDurations = 
				new long[lastFrameIndex - firstFrameIndex + 1];
		
		for(int i = 0; i < framesDurations.length; i++) {
			framesDurations[i] = getFrameDuration(firstFrameIndex + i);
		}
		
		animate(framesDurations, firstFrameIndex, 
				lastFrameIndex, false, mAnimationListener);
	}
	
	protected abstract int getFirstFrameIndex();
	protected abstract int getLastFrameIndex();
	protected abstract long getFrameDuration(int frameIndex);
	
}
