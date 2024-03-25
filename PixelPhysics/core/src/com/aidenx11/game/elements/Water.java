package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Water extends Element {
	
	public static ElementTypes type = ElementTypes.WATER;
	private float velocity = 0f;
	private float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private float maxSpeed = 9f;
	private float density = 6f;

	public Water(int row, int column) {
		super(row, column, new CustomColor(ColorValues.WATER), false);
		super.setMovable(true);
		super.setModified(true);
		super.setMovesDown(true);
		super.setDensity(density);
		super.setMovesSideways(true);
		super.setLimitedLife(false);
	}
	
	@Override
	public ElementTypes getType() {
		return type;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float newVelocity) {
		this.velocity = newVelocity;
	}
	
	public void resetVelocity() {
		setVelocity(0);
	}

	@Override
	public float getMaxSpeed() {
		return maxSpeed;
	}

	@Override
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public float getAcceleration() {
		return acceleration;
	}

	@Override
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public boolean isFlammable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean burnsThings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean extinguishesThings() {
		return true;
	}

	@Override
	public float getChanceToCatch() {
		return 0;
	}

}
