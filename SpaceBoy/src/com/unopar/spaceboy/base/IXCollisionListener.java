package com.unopar.spaceboy.base;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;

import com.unopar.spaceboy.character.Enemy;
import com.unopar.spaceboy.character.Weapon;

public interface IXCollisionListener {
	AnimatedSprite getMainCharacter();
	void onCollision(AnimatedSprite obstacle);
	
	ArrayList<Enemy> getEnemyCharacters();
	void onCollision(Weapon weapon, Enemy enemy);
}
