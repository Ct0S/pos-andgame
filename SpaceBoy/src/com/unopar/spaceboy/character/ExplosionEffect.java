package com.unopar.spaceboy.character;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class ExplosionEffect extends Effect {
	public ExplosionEffect(Sound sound, ITiledTextureRegion textureRegion,
			Engine engine) {
		super(sound, textureRegion, engine);
	}

	@Override
	protected int getFirstFrameIndex() {
		return ((6 - 1) * 16);
	}

	@Override
	protected int getLastFrameIndex() {
		return getFirstFrameIndex() + 15;
	}

	@Override
	protected long getFrameDuration(int frameIndex) {
		return 50;
	}
}
