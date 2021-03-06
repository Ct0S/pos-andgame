package com.unopar.spaceboy.joystick;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;

import com.unopar.spaceboy.character.SpaceBoy;

public class ControlListener implements IAnalogOnScreenControlListener {
	private SpaceBoy mSpaceBoy;
	
	public ControlListener(SpaceBoy spaceBoy) {
		mSpaceBoy = spaceBoy;
	}

	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
			float pValueX, float pValueY) {
		mSpaceBoy.move(pValueX, pValueY);
	}

	@Override
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
	}
}
