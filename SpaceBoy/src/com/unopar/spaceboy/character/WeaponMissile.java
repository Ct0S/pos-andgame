package com.unopar.spaceboy.character;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.unopar.spaceboy.base.IXCollisionListener;

public class WeaponMissile extends Weapon {
	public WeaponMissile(ITiledTextureRegion textureRegion,
			VertexBufferObjectManager vertexBuffer,
			IXCollisionListener collisionListener) {
		super(textureRegion, vertexBuffer, collisionListener);
		
		setRotation(90);
	}

	@Override
	protected boolean animateLaunch() {
		animate(0, 3, false);
		move(1, 0);
		
		return true;
	}

	@Override
	protected void animateMove() {
		animate(4, 12, true);
		move(5, 0);
	}

	@Override
	protected boolean animateCollides() {		
		return false;
	}

	@Override
	protected int getSpeed() {
		return 100;
	}

	@Override
	protected int getFrameDurations(int frameIndex) {		
		return 50;
	}
}
