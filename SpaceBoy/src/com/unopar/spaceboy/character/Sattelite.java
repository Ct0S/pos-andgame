package com.unopar.spaceboy.character;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.unopar.spaceboy.base.IXCollisionListener;

public class Sattelite extends Enemy {
	public Sattelite(ITiledTextureRegion textureRegion,
			Engine engine, IXCollisionListener collisionListener) {
		super(0, 0, textureRegion, engine, collisionListener);
		
		resetPosition(true);
	}

	@Override
	protected int getDefaultSpeed() {		
		return 200;
	}
}
