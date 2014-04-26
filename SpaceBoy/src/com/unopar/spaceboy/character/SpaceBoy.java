package com.unopar.spaceboy.character;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.unopar.spaceboy.base.XApplication;

public class SpaceBoy extends AnimatedSprite {
	public enum States {
		None,

		Fowarding, Back
	}

	private int mSpeed;
	private States mState;
	private PhysicsHandler mPhysicalHandler;
	
	private final int mLimiteX;
	private final int mLimiteY;
	
	public SpaceBoy(float pX, float pY, TiledTextureRegion textureRegion,
			Engine engine) {
		super(pX, pY, textureRegion, engine.getVertexBufferObjectManager());
		
		mSpeed = 200;
		
		mPhysicalHandler = new PhysicsHandler(this);
		
		registerUpdateHandler(mPhysicalHandler);
		
		setState(States.Fowarding);
		
		mLimiteX = (int) (engine.getCamera().getWidth() - textureRegion.getWidth());
		mLimiteY = (int) (engine.getCamera().getHeight() - textureRegion.getHeight());
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(mX < 0) {
			mPhysicalHandler.setVelocityX(mSpeed);
		} else if(mX >= mLimiteX) {
			mPhysicalHandler.setVelocityX(-mSpeed);
		}
		
		if(mY < 0) {
			mPhysicalHandler.setVelocityY(mSpeed);
		} else if(mY >= mLimiteY) {
			mPhysicalHandler.setVelocityY(-mSpeed);
		}
		
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	public void move(float x, float y) {
		if(x < 0) {
			setState(States.Back);
		} else {
			setState(States.Fowarding);
		}
		
		mPhysicalHandler.setVelocity(x * mSpeed, y * mSpeed);
	}

	private void setState(States newState) {
		if (mState == newState) {
			return;
		}

		mState = newState;
		switch (mState) {
		case Fowarding:
			animate(new long[] { mSpeed, mSpeed, mSpeed }, 4, 6, true);
			break;
		case Back:
			animate(new long[] { mSpeed, mSpeed, mSpeed }, 1, 3, true);
		}
	}

}
