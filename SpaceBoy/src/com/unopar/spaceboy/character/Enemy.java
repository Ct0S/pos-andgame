package com.unopar.spaceboy.character;

import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.unopar.spaceboy.base.IXCollisionListener;
import com.unopar.spaceboy.base.XApplication;

public abstract class Enemy extends AnimatedSprite {
	private PhysicsHandler mPhysicalHandler;
	private int mSpeed;
	private Random mRandom = new Random();
	private float mLimiteX;
	private float mLimiteY;
	
	private IXCollisionListener mCollisionListener;

	public Enemy(float pX, float pY, 
			ITiledTextureRegion textureRegion, Engine engine,
			IXCollisionListener collisionListener) {
		super(pX, pY, textureRegion, engine.getVertexBufferObjectManager());
		
		mPhysicalHandler = new PhysicsHandler(this);
		registerUpdateHandler(mPhysicalHandler);
		
		mSpeed = getDefaultSpeed();
		
		mLimiteX = XApplication.getInstance().getScreenWidth();
		mLimiteY = XApplication.getInstance().getScreenHeight() - 
				textureRegion.getHeight();
		
		mCollisionListener = collisionListener;
	}
	
	public void move() {
		resetPosition(false);
	}
	
	public void collide() {
		resetPosition(true);
	}

	protected void resetPosition(boolean resetXY) {
		if(resetXY) {
			float pX = mLimiteX - getTextureRegion().getWidth();
			float pY = mRandom.nextFloat() * mLimiteY;
			
			setPosition(pX, pY);
		}
		
		float velocityX = -((mRandom.nextInt(2) + 1) * mSpeed);
		float velocityY = mRandom.nextBoolean()
				? (mRandom.nextFloat() * mSpeed)
				: -(mRandom.nextFloat() * mSpeed);				
		
		mPhysicalHandler.setVelocity(velocityX, velocityY);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		
		if(mX + getTextureRegion().getWidth() < 0) {
			resetPosition(true);
		} else {
			if(mY < 0 || mY > mLimiteY) {
				mPhysicalHandler.setVelocityY(
						-mPhysicalHandler.getVelocityY());
			}
			
			if(collidesWith(mCollisionListener.getMainCharacter())) {
				mCollisionListener.onCollision(this);
				
				resetPosition(true);
			}
		}
	}

	protected abstract int getDefaultSpeed();

}
